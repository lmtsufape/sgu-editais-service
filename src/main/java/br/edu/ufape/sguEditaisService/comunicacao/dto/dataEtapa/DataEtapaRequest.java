//package br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa;
//
//import br.edu.ufape.sguEditaisService.models.DataEtapa;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//
//@Getter @Setter @NoArgsConstructor
//public class DataEtapaRequest {
//    @NotNull(message = "A data de início é obrigatória.")
//    private LocalDateTime dataInicio;
//
//    @NotNull(message = "A data de fim é obrigatória.")
//    private LocalDateTime dataFim;
//
//    @NotNull(message = "O ID da etapa (modelo) é obrigatório.")
//    private Long etapaId;
//
//    @NotNull(message = "O ID do status inicial da etapa é obrigatório.")
//    private Long statusId;
//
//    public DataEtapa converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, DataEtapa.class);
//    }
//}