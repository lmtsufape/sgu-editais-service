//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//public class Inscricao {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // A âncora do candidato (independente de ser aluno UFAPE ou externo via Extra SiSU)
//    @Column(nullable = false)
//    private UUID userId;
//
//    // Só recebe valor na hora que o candidato clica em "Enviar" definitivamente
//    @Column(unique = true)
//    private String numeroProtocolo;
//
//    // Data da submissão final
//    private LocalDateTime dataInscricao;
//
////    // Status do motor do sistema (Controla as engrenagens: se é RASCUNHO, não processa)
////    @Enumerated(EnumType.STRING)
////    @Column(nullable = false)
////    private SituacaoInscricao situacao = SituacaoInscricao.RASCUNHO;
//
//    // Status Dinâmico (Para a comissão dar feedback customizado ao candidato, ex: "Falta RG")
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "status_personalizado_id", nullable = false)
//    private StatusPersonalizado statusPersonalizado;
//
//    // A qual edital essa inscrição pertence
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "edital_id", nullable = false)
//    private Edital edital;
//
//    // Respostas do formulário (Blindadas: Apagou inscrição, apaga as respostas)
//    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ValorCampo> valoresCampos = new ArrayList<>();
//
//    // Auditoria (Blindada: Apagou inscrição, apaga o rastro dela)
//    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<HistoricoEtapaInscricao> historico = new ArrayList<>();
//
//    // ================== MÉTODOS AUXILIARES ================== //
//
//    public void adicionarValorCampo(ValorCampo valorCampo) {
//        valorCampo.setInscricao(this);
//        this.valoresCampos.add(valorCampo);
//    }
//
//    public void adicionarHistorico(HistoricoEtapaInscricao historicoEtapa) {
//        historicoEtapa.setInscricao(this);
//        this.historico.add(historicoEtapa);
//    }
//}

package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.SituacaoInscricao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Intercepta o DELETE e faz UPDATE
@SQLDelete(sql = "UPDATE inscricao SET ativo = false WHERE id = ?")
// Força que qualquer SELECT só traga os ativos
@SQLRestriction("ativo = true")

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A âncora do candidato integrada via Keycloak
    @Column(nullable = false)
    private UUID userId;

    @Column(unique = true)
    private String numeroProtocolo;

    private LocalDateTime dataInscricao;

    @Column(nullable = false)
    private boolean ativo = true;

    // RESTAURADO: Status interno da máquina de estados (RASCUNHO vs SUBMETIDA)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoInscricao situacao = SituacaoInscricao.RASCUNHO;

    // Status do negócio visualizado pelo candidato e comissão (ex: DEFERIDO, AGUARDANDO PAGAMENTO)
    @ManyToOne(optional = false)
    @JoinColumn(name = "status_personalizado_id", nullable = false)
    private StatusPersonalizado statusPersonalizado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "edital_id", nullable = false)
    private Edital edital;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValorCampo> valoresCampos = new ArrayList<>();

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoEtapaInscricao> historico = new ArrayList<>();

    // ================== MÉTODOS AUXILIARES ================== //

    public void adicionarValorCampo(ValorCampo valorCampo) {
        valorCampo.setInscricao(this);
        this.valoresCampos.add(valorCampo);
    }

    public void adicionarHistorico(HistoricoEtapaInscricao historicoEtapa) {
        historicoEtapa.setInscricao(this);
        this.historico.add(historicoEtapa);
    }
}