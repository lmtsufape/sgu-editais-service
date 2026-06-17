//package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;
//
//import br.edu.ufape.sguEditaisService.models.ValorCampo;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class ValorCampoResponse {
//    private Long id;
//    private String valor;
//    private CampoPersonalizadoResponse campoPersonalizado;
//
//    public ValorCampoResponse(ValorCampo valorCampo, ModelMapper modelMapper) {
//        this.id = valorCampo.getId();
//        this.valor = valorCampo.getValor();
//        if (valorCampo.getCampoPersonalizado() != null) {
//            this.campoPersonalizado = new CampoPersonalizadoResponse(valorCampo.getCampoPersonalizado(), modelMapper);
//        }
//    }
//}