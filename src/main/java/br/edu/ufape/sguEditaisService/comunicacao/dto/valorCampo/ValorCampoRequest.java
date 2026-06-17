//package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;
//
//import br.edu.ufape.sguEditaisService.models.ValorCampo;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class ValorCampoRequest {
//
//    @NotNull(message = "O ID do campo é obrigatório.")
//    private Long campoId;
//
//    // Se for texto, vem o texto. Se for ficheiro, o frontend deve mandar aqui o NOME ORIGINAL do ficheiro
//    // para o Backend conseguir fazer o "match" com o MultipartFile recebido.
//    private String valor;
//
//    public ValorCampo converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, ValorCampo.class);
//    }
//}