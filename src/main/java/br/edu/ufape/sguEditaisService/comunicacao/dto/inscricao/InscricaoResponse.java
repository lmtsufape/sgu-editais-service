//package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;
//
//import br.edu.ufape.sguEditaisService.models.Inscricao;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Getter @Setter @NoArgsConstructor
//public class InscricaoResponse {
//    private Long id;
//    private UUID userId;
//    private String numeroProtocolo;
//    private LocalDateTime dataInscricao;
//    private StatusPersonalizadoResponse statusPersonalizado;
//    private List<ValorCampoResponse> valoresCampos;
//    private List<HistoricoEtapaInscricaoResponse> historico;
//
//    public InscricaoResponse(Inscricao inscricao, ModelMapper modelMapper) {
//        this.id = inscricao.getId();
//        this.userId = inscricao.getUserId();
//        this.numeroProtocolo = inscricao.getNumeroProtocolo();
//        this.dataInscricao = inscricao.getDataInscricao();
//
//        if (inscricao.getStatusPersonalizado() != null) {
//            this.statusPersonalizado = new StatusPersonalizadoResponse(inscricao.getStatusPersonalizado(), modelMapper);
//        }
//        if (inscricao.getValoresCampos() != null) {
//            this.valoresCampos = inscricao.getValoresCampos().stream()
//                    .map(v -> new ValorCampoResponse(v, modelMapper))
//                    .collect(Collectors.toList());
//        }
//        if (inscricao.getHistorico() != null) {
//            this.historico = inscricao.getHistorico().stream()
//                    .map(h -> new HistoricoEtapaInscricaoResponse(h, modelMapper))
//                    .collect(Collectors.toList());
//        }
//    }
//}