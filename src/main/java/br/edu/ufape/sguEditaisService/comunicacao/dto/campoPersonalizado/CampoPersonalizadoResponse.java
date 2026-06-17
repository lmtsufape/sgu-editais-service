//package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;
//
//import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class CampoPersonalizadoResponse {
//    private Long id;
//    private String titulo;
//    private TipoCampo tipoCampo;
//    private boolean obrigatorio;
//
//    public CampoPersonalizadoResponse(CampoPersonalizado campo, ModelMapper modelMapper) {
//        modelMapper.map(campo, this);
//    }
//}