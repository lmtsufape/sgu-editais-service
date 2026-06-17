//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//public class HistoricoEtapaInscricao {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDateTime dataMudanca;
//
//    @ManyToOne
//    @JoinColumn(name = "status_anterior_id")
//    private StatusPersonalizado statusAnterior;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "status_novo_id", nullable = false)
//    private StatusPersonalizado statusNovo;
//
//    @ManyToOne
//    @JoinColumn(name = "etapa_id")
//    private Etapa etapa;
//
//    private UUID responsavelId; // Quem mudou? (Candidato ou Comissão de avaliação)
//
//    @Column(columnDefinition = "TEXT")
//    private String parecer;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "inscricao_id", nullable = false)
//    private Inscricao inscricao;
//}

package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter // Mantemos para o Hibernate/ModelMapper, mas o banco de dados vai bloquear Updates!
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoEtapaInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // updatable = false garante que um UPDATE gerado acidentalmente seja ignorado pelo banco
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMudanca;

    @ManyToOne
    @JoinColumn(name = "status_anterior_id", updatable = false)
    private StatusPersonalizado statusAnterior;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_novo_id", nullable = false, updatable = false)
    private StatusPersonalizado statusNovo;

    @ManyToOne
    @JoinColumn(name = "etapa_id", updatable = false)
    private Etapa etapa;

    // Quem fez a mudança? (Pode ser o UUID do avaliador ou do próprio candidato ao submeter)
    @Column(nullable = false, updatable = false)
    private UUID responsavelId;

    @Column(columnDefinition = "TEXT", updatable = false)
    private String parecer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inscricao_id", nullable = false, updatable = false)
    private Inscricao inscricao;
}