//package br.edu.ufape.sguEditaisService.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter @AllArgsConstructor @NoArgsConstructor
//public class DocumentoEdital {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nome;
//    private String caminho;
//    private LocalDateTime dataUpload;
//
//    @ManyToOne
//    @JsonIgnore
//    private Edital edital;
//}

package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentoEdital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String path; // Caminho do PDF oficial publicado no Storage
}