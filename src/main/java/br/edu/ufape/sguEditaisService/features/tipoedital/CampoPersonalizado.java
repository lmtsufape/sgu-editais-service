package br.edu.ufape.sguEditaisService.features.tipoedital;

import br.edu.ufape.sguEditaisService.features.tipoedital.dto.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@MappedSuperclass // Mágica do JPA para herança sem misturar tabelas
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CampoPersonalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private TipoEditalResponse tipoEdital;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCampo tipoCampo;

    @Column(nullable = false)
    private boolean obrigatorio = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String configuracoes;

    // Construtor protegido para ser usado pelas classes filhas
    protected CampoPersonalizado(String titulo, TipoCampo tipoCampo, TipoEditalResponse tipoEdital, boolean obrigatorio, String configuracoes) {
        this.titulo = titulo;
        this.tipoCampo = tipoCampo;
        this.tipoEdital = tipoEdital;
        this.obrigatorio = obrigatorio;
        this.configuracoes = configuracoes;
    }
}