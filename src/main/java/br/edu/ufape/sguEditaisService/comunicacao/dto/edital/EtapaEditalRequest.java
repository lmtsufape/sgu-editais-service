//package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import java.time.LocalDateTime;
//
//@Getter @Setter @NoArgsConstructor
//public class EtapaEditalRequest {
//    // Dados físicos da Etapa
//    @NotBlank(message = "O nome da etapa é obrigatório.")
//    private String nome;
//
//    private String descricao;
//
//    @NotNull(message = "A ordem é obrigatória.")
//    private Integer ordem;
//
//    // Dados temporais (Cronograma / DataEtapa)
//    @NotNull(message = "A data de início é obrigatória.")
//    private LocalDateTime dataInicio;
//
//    @NotNull(message = "A data de fim é obrigatória.")
//    private LocalDateTime dataFim;
//
//    @NotNull(message = "O ID do status inicial da etapa é obrigatório.")
//    private Long statusId;
//}