//package br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa;
//
//import br.edu.ufape.sguEditaisService.models.DataEtapa;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//
//@Getter @Setter @NoArgsConstructor
//public class DataEtapaResponse {
//    private Long id;
//    private LocalDateTime dataInicio;
//    private LocalDateTime dataFim;
//    private EtapaResponse etapa;
//    private StatusPersonalizadoResponse status;
//
//    public DataEtapaResponse(DataEtapa dataEtapa, ModelMapper modelMapper) {
//        this.id = dataEtapa.getId();
//        this.dataInicio = dataEtapa.getDataInicio();
//        this.dataFim = dataEtapa.getDataFim();
//        if (dataEtapa.getEtapa() != null) {
//            this.etapa = new EtapaResponse(dataEtapa.getEtapa(), modelMapper);
//        }
//        if (dataEtapa.getStatus() != null) {
//            this.status = new StatusPersonalizadoResponse(dataEtapa.getStatus(), modelMapper);
//        }
//    }
//}