//package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;
//
//import br.edu.ufape.sguEditaisService.models.Etapa;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class EtapaResponse {
//    private Long id;
//    private String nome;
//    private String descricao;
//    private Integer ordem;
//
//    public EtapaResponse(Etapa etapa, ModelMapper modelMapper) {
//        modelMapper.map(etapa, this);
//    }
//}