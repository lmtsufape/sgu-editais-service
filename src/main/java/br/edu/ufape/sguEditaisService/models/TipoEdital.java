//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//public class TipoEdital {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String nome;
//
//    private String descricao;
//
//    // As etapas canônicas que todo edital desse tipo DEVE ter
//    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Etapa> etapas = new ArrayList<>();
//
//    // O formulário padrão para esse tipo de edital
//    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CampoPersonalizado> campos = new ArrayList<>();
//}

package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

// Intercepta o DELETE e faz UPDATE
@SQLDelete(sql = "UPDATE tipo_edital SET ativo = false WHERE id = ?")
// Força que qualquer SELECT só traga os ativos
@SQLRestriction("ativo = true")

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoEdital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

    // NOVO: Isolamento de domínio. Define qual serviço é "dono" deste template (ex: "sgu-prae-service")
    @Column(nullable = false)
    private String moduloOrigem;

    // As etapas canônicas que todo edital desse tipo DEVE ter
    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas = new ArrayList<>();

    // O formulário padrão para esse tipo de edital
    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoPersonalizado> campos = new ArrayList<>();
}