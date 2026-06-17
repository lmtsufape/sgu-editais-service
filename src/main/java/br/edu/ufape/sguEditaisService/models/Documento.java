//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class Documento {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nome;
//    private String path;
//}

package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O nome original do ficheiro (ex: "edital_ingressa_jovem.pdf")
    @Column(nullable = false)
    private String nome;

    // O caminho real no disco, MinIO ou bucket S3
    // Usamos TEXT para garantir que URLs longos de cloud storage não sejam cortados
    @Column(nullable = false, columnDefinition = "TEXT")
    private String path;
}