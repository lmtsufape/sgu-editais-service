//package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;
//
//import br.edu.ufape.sguEditaisService.models.Etapa;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class EtapaRequest {
//    @NotBlank(message = "O nome da etapa é obrigatório.")
//    private String nome;
//
//    private String descricao;
//
//    @NotNull(message = "A ordem da etapa é obrigatória.")
//    @Positive(message = "A ordem deve ser maior que zero.")
//    private Integer ordem;
//
//    public Etapa converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, Etapa.class);
//    }
//}