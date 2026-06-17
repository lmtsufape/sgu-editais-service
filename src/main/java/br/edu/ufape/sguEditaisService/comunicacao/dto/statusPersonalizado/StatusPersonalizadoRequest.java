//package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;
//
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//@Getter @Setter @NoArgsConstructor
//public class StatusPersonalizadoRequest {
//    @NotBlank(message = "O nome do status é obrigatório.")
//    private String nome;
//
//    private String descricao;
//
//    @NotNull(message = "O tipo de status é obrigatório.")
//    private TipoStatus tipoStatus;
//
//    public StatusPersonalizado converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, StatusPersonalizado.class);
//    }
//}