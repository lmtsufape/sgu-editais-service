//package br.edu.ufape.sguEditaisService.models;
//
//import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class CampoPersonalizado {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String titulo;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TipoCampo tipoCampo;
//
//    private boolean obrigatorio = true;
//
//    @ManyToOne
//    @JoinColumn(name = "tipo_edital_id")
//    private TipoEdital tipoEdital;
//
//    // Vínculo do Snapshot
//    @ManyToOne
//    @JoinColumn(name = "edital_id")
//    private Edital edital;
//}

package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CampoPersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCampo tipoCampo;

    private boolean obrigatorio = true;

    // NOVO: Metadados de validação flexíveis mapeados para JSONB no PostgreSQL.
    // Ex: {"maxLength": 200}, {"tamanhoMaximoMB": 5, "extensoes": ["pdf", "jpg"]}, ou {"opcoes": ["Sim", "Não"]}
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String configuracoes;

    @ManyToOne
    @JoinColumn(name = "tipo_edital_id")
    private TipoEdital tipoEdital;

    // Vínculo do Snapshot
    @ManyToOne
    @JoinColumn(name = "edital_id")
    private Edital edital;
}