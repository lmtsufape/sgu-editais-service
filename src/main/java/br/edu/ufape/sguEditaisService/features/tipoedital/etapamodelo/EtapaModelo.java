/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo;

import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.CampoEtapa;
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

public class EtapaModelo {
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

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    private LocalDateTime dataFim;

    // O coração da flexibilidade. Mapeia perfeitamente para JSONB no PostgreSQL.
    // Aqui você guarda: {"perfisAvaliadores": ["ROLE_X"], "valorTaxa": 50.00, "isencaoPermitida": true}
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String configuracoes;

    // Se pertencer ao Molde (TipoEdital)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_edital_id", nullable = false)
    private TipoEdital tipoEdital;

    @OneToMany(mappedBy = "etapaModelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CampoEtapa> camposEtapa = new ArrayList<>();

    public void adicionarCampo(CampoEtapa campo) {
        this.camposEtapa.add(campo);
        campo.vincularAEtapa(this);
    }

    public static EtapaModelo criar(
            String nome, String descricao, Integer ordem,
            LocalDateTime dataInicio, LocalDateTime dataFim, String configuracoes
    ) {
        EtapaModelo etapa = new EtapaModelo();
        etapa.nome = nome;
        etapa.descricao = descricao;
        etapa.ordem = ordem;
        etapa.dataInicio = dataInicio;
        etapa.dataFim = dataFim;
        etapa.configuracoes = configuracoes;
        return etapa;
    }

    public void vincularAoTipo(TipoEdital tipoEdital) {
        this.tipoEdital = tipoEdital;
    }

    public void desvincularAoTipo() {
        this.tipoEdital = null;
    }


    public void removerCampo(CampoEtapa campo) {
        this.camposEtapa.remove(campo);
        campo.desvincular();
    }
}

 */