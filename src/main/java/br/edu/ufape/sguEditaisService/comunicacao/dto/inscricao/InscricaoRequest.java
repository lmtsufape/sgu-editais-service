//package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Getter @Setter @NoArgsConstructor
//public class InscricaoRequest {
//
//    private Long id;
//
//    @NotNull(message = "O ID do edital é obrigatório.")
//    private Long editalId;
//
//    @NotNull(message = "O ID do status da inscrição é obrigatório.")
//    private Long statusId;
//
//    @Valid
//    private List<ValorCampoRequest> valores = new ArrayList<>();
//}