//package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter @Setter @NoArgsConstructor
//public class AvaliacaoInscricaoRequest {
//
//    @NotNull(message = "O ID do novo status é obrigatório.")
//    private Long novoStatusId;
//
//    @NotNull(message = "O ID da etapa sendo avaliada é obrigatório.")
//    Long etapaId;
//
//    @NotBlank(message = "O parecer da avaliação é obrigatório.")
//    private String parecer;
//
//}