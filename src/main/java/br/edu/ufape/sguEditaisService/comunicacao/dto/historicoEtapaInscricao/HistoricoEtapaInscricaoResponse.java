//package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;
//
//import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter @Setter @NoArgsConstructor
//public class HistoricoEtapaInscricaoResponse {
//    private Long id;
//    private LocalDateTime dataMudanca;
//    private UUID responsavelId;
//    private String parecer;
//    private StatusPersonalizadoResponse statusAnterior;
//    private StatusPersonalizadoResponse statusNovo;
//
//    public HistoricoEtapaInscricaoResponse(HistoricoEtapaInscricao historico, ModelMapper modelMapper) {
//        this.id = historico.getId();
//        this.dataMudanca = historico.getDataMudanca();
//        this.responsavelId = historico.getResponsavelId();
//        this.parecer = historico.getParecer();
//
//        if (historico.getStatusAnterior() != null) {
//            this.statusAnterior = new StatusPersonalizadoResponse(historico.getStatusAnterior(), modelMapper);
//        }
//        if (historico.getStatusNovo() != null) {
//            this.statusNovo = new StatusPersonalizadoResponse(historico.getStatusNovo(), modelMapper);
//        }
//    }
//}