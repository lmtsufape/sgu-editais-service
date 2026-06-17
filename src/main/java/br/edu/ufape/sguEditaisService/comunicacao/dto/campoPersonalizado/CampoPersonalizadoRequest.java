//package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;
//
//import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class CampoPersonalizadoRequest {
//    @NotBlank(message = "O título do campo é obrigatório.")
//    private String titulo;
//
//    @NotNull(message = "O tipo do campo é obrigatório.")
//    private TipoCampo tipoCampo;
//
//    private boolean obrigatorio = true;
//
//    public CampoPersonalizado converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, CampoPersonalizado.class);
//    }
//}