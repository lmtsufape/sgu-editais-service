//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class Etapa {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String nome;
//
//    private String descricao;
//
//    private Integer ordem;
//
//    // Se pertencer ao Molde (TipoEdital), preenche isto:
//    @ManyToOne
//    @JoinColumn(name = "tipo_edital_id")
//    private TipoEdital tipoEdital;
//
//    // Se for o Snapshot de um Edital real, preenche isto:
//    @ManyToOne
//    @JoinColumn(name = "edital_id")
//    private Edital edital;
//}

package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.TipoEtapa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private Integer ordem;

    // NOVO: Define qual é o comportamento/regras dessa etapa específica
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEtapa tipoDaEtapa;

    // NOVO: Datas trazidas da extinta entidade DataEtapa
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    // NOVO: O coração da flexibilidade. Mapeia perfeitamente para JSONB no PostgreSQL.
    // Aqui você guarda: {"perfisAvaliadores": ["ROLE_X"], "valorTaxa": 50.00, "isencaoPermitida": true}
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String configuracoes;

    // Se pertencer ao Molde (TipoEdital)
    @ManyToOne
    @JoinColumn(name = "tipo_edital_id")
    private TipoEdital tipoEdital;

    // Se for o Snapshot de um Edital real
    @ManyToOne
    @JoinColumn(name = "edital_id")
    private Edital edital;
}