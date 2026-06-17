//package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;
//
//import br.edu.ufape.sguEditaisService.models.TipoEdital;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter @Setter @NoArgsConstructor
//public class TipoEditalRequest {
//    @NotBlank(message = "O nome do modelo de edital é obrigatório.")
//    private String nome;
//
//    private String descricao;
//
//    @Valid
//    private List<EtapaRequest> etapas = new ArrayList<>();
//
//    @Valid
//    private List<CampoPersonalizadoRequest> campos = new ArrayList<>();
//
//    public TipoEdital converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, TipoEdital.class);
//    }
//}