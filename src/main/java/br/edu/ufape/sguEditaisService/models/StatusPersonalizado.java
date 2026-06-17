//package br.edu.ufape.sguEditaisService.models;
//
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class StatusPersonalizado {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nome;
//    private String descricao;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TipoStatus tipoStatus;
//}

package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

// Intercepta o DELETE e faz UPDATE
@SQLDelete(sql = "UPDATE status_personalizado SET ativo = false WHERE id = ?")
// Força que qualquer SELECT só traga os ativos
@SQLRestriction("ativo = true")

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StatusPersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoStatus tipoStatus;

    @Column(nullable = false)
    private boolean ativo = true;
}