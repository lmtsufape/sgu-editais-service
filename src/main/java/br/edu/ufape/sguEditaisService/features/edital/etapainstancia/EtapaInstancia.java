package br.edu.ufape.sguEditaisService.features.edital.etapainstancia;

import br.edu.ufape.sguEditaisService.features.edital.Edital;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.campoetapainstancia.CampoEtapaInstancia;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EtapaInstancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    private String descricao;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer ordem;

    // As datas reais de quando a etapa vai acontecer!
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @Column(nullable = false)
    private boolean herdadoDoModelo = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String configuracoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edital_id", nullable = false)
    private Edital edital;

    @OneToMany(mappedBy = "etapaInstancia", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CampoEtapaInstancia> campos = new ArrayList<>();

    public static EtapaInstancia clonarDe(String nome, String descricao, Integer ordem, String configuracoes) {
        EtapaInstancia etapa = new EtapaInstancia();
        etapa.nome = nome;
        etapa.descricao = descricao;
        etapa.ordem = ordem;
        etapa.configuracoes = configuracoes;
        return etapa;
    }

    public void vincularAoEdital(Edital edital) {
        this.edital = edital;
    }

    public void adicionarCampo(CampoEtapaInstancia campo) {
        this.campos.add(campo);
        campo.vincularAEtapa(this);
    }

    public void definirDatas(LocalDateTime inicio, LocalDateTime fim) {
        this.dataInicio = inicio;
        this.dataFim = fim;
    }

    public void marcarComoHerdado() {
        this.herdadoDoModelo = true;
    }

}