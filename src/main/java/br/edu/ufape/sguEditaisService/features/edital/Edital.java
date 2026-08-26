package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.features.edital.campoedital.CampoEdital;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE edital SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Edital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(name = "curso_id")
    private Long cursoId;

    @NotBlank
    @Column(nullable = false)
    private String moduloOrigem;

    @Column(nullable = false)
    private boolean ativo = true;

    // Controla a visibilidade/andamento do edital instanciado para o Motor
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoEdital situacao = SituacaoEdital.PLANEJAMENTO;

    // O Molde do qual este Edital foi originado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_edital_id", nullable = false, updatable = false)
    private TipoEdital tipoEditalOrigem;

    @Column(nullable = false)
    private boolean herdadoDoModelo = false;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EtapaInstancia> etapas = new ArrayList<>();

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CampoEdital> camposGlobais = new ArrayList<>();

    // Mutações e Comportamentos (DDD)
    public static Edital instanciarDe(TipoEdital tipoEdital, String titulo, Long cursoId) {
        Edital edital = new Edital();
        edital.titulo = titulo;
        edital.moduloOrigem = tipoEdital.getModuloOrigem();
        edital.tipoEditalOrigem = tipoEdital;
        edital.cursoId = cursoId;
        return edital;
    }

    public void adicionarEtapa(EtapaInstancia etapa) {
        this.etapas.add(etapa);
        etapa.vincularAoEdital(this);
    }

    public void adicionarCampoGlobal(CampoEdital campo) {
        this.camposGlobais.add(campo);
        campo.vincularAoEdital(this);
    }

    public void checarPermissaoEdicao() {
        if (this.situacao != SituacaoEdital.PLANEJAMENTO) {
            throw new RegraNegocioException("Editais só podem ter sua estrutura alterada enquanto estiverem em PLANEJAMENTO.");
        }
    }

    // Record interno
    public record PrazoEtapa(Long etapaId, LocalDateTime dataInicio, LocalDateTime dataFim) {}

    // AÇÃO A: Salva o rascunho do cronograma validando o que já foi preenchido
    public void salvarCronograma(List<PrazoEtapa> prazosRecebidos) {
        this.checarPermissaoEdicao(); // Garante que está em PLANEJAMENTO

        // 1. Aplica as datas enviadas
        for (PrazoEtapa prazo : prazosRecebidos) {
            EtapaInstancia etapa = this.etapas.stream()
                    .filter(e -> e.getId().equals(prazo.etapaId()))
                    .findFirst()
                    .orElseThrow(() -> new RegraNegocioException("Etapa ID " + prazo.etapaId() + " não pertence a este edital."));

            if (prazo.dataInicio() != null && prazo.dataFim() != null && prazo.dataInicio().isAfter(prazo.dataFim())) {
                throw new RegraNegocioException("Na etapa '" + etapa.getNome() + "', a data de início não pode ser posterior à data de fim.");
            }
            etapa.definirDatas(prazo.dataInicio(), prazo.dataFim());
        }

        // 2. Validação Cronológica Inteligente (ignora as etapas que ainda não têm data)
        List<EtapaInstancia> etapasOrdenadas = new ArrayList<>(this.etapas);
        etapasOrdenadas.sort((e1, e2) -> e1.getOrdem().compareTo(e2.getOrdem()));

        EtapaInstancia etapaAnteriorComData = null;
        for (EtapaInstancia etapaAtual : etapasOrdenadas) {
            if (etapaAtual.getDataInicio() == null) continue; // Pula as que estão "em branco"

            if (etapaAnteriorComData != null && etapaAtual.getDataInicio().isBefore(etapaAnteriorComData.getDataInicio())) {
                throw new RegraNegocioException(
                        "Conflito Cronológico: A etapa '" + etapaAtual.getNome() + "' (Ordem " + etapaAtual.getOrdem() +
                                ") não pode começar antes da etapa '" + etapaAnteriorComData.getNome() + "' (Ordem " + etapaAnteriorComData.getOrdem() + ")."
                );
            }
            etapaAnteriorComData = etapaAtual;
        }
    }

    // AÇÃO B: O Guardião da Publicação Final
    public void publicar() {
        this.checarPermissaoEdicao();

        if (this.etapas.isEmpty()) {
            throw new RegraNegocioException("Não é possível publicar um edital sem etapas.");
        }

        // Exige rigorosamente que nada esteja vazio
        for (EtapaInstancia etapa : this.etapas) {
            if (etapa.getDataInicio() == null || etapa.getDataFim() == null) {
                throw new RegraNegocioException("Falha na publicação: A etapa '" + etapa.getNome() + "' está sem datas definidas.");
            }
        }

        this.situacao = SituacaoEdital.PUBLICADO;
    }

    public EtapaInstancia obterEtapaVigente() {
        LocalDateTime agora = LocalDateTime.now();

        return this.etapas.stream()
                .filter(e -> e.getDataInicio() != null && e.getDataFim() != null) // Ignora etapas não agendadas
                .filter(e -> !agora.isBefore(e.getDataInicio()) && !agora.isAfter(e.getDataFim())) // Checa a intersecção do tempo
                .findFirst()
                .orElse(null);
    }

    public void marcarComoHerdado() {
        this.herdadoDoModelo = true;
    }

}