//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class ValorCampo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Guarda o texto digitado OU o caminho do arquivo (se for TipoCampo.ARQUIVO)
//    @Column(columnDefinition = "TEXT")
//    private String valor;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "inscricao_id", nullable = false)
//    private Inscricao inscricao;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "campo_id", nullable = false)
//    private CampoPersonalizado campoPersonalizado;
//}

package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ValorCampo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se o campo for textual, armazena a resposta aqui
    @Column(columnDefinition = "TEXT")
    private String valor;

    // Se o campo for do tipo ARQUIVO, aponta para o registro na tabela de documentos
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_id")
    private Documento documento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "campo_id", nullable = false)
    private CampoPersonalizado campoPersonalizado;
}