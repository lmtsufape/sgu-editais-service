//package br.edu.ufape.sguEditaisService.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class DataEtapa {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private LocalDateTime dataInicio;
//
//    @Column(nullable = false)
//    private LocalDateTime dataFim;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "edital_id", nullable = false)
//    private Edital edital;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "etapa_id", nullable = false)
//    private Etapa etapa;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "status_personalizado_id", nullable = false)
//    private StatusPersonalizado status;
//}