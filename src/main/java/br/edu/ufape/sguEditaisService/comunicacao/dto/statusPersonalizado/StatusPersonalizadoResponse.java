//package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;
//
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class StatusPersonalizadoResponse {
//    private Long id;
//    private String nome;
//    private String descricao;
//    private TipoStatus tipoStatus;
//
//    public StatusPersonalizadoResponse(StatusPersonalizado status, ModelMapper modelMapper) {
//        modelMapper.map(status, this);
//    }
//}