//package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter @Setter @NoArgsConstructor
//public class EditalResponse {
//    private Long id;
//    private String titulo;
//
//    private CursoResponse curso;
//
//    private LocalDateTime dataInicio;
//    private LocalDateTime dataFim;
//    private boolean ativo;
//    private DocumentoResponse documentoEdital;
//    private StatusPersonalizadoResponse status;
//    private TipoEditalResponse tipoEdital;
//    private List<DataEtapaResponse> cronograma;
//    private List<EtapaResponse> etapas;
//    private List<CampoPersonalizadoResponse> campos;
//
//    public EditalResponse(Edital edital, ModelMapper modelMapper) {
//        this.id = edital.getId();
//        this.titulo = edital.getTitulo();
//        this.dataInicio = edital.getDataInicio();
//        this.dataFim = edital.getDataFim();
//        this.ativo = edital.isAtivo();
//
//        if (edital.getDocumentoEdital() != null) {
//            this.documentoEdital = new DocumentoResponse(edital.getDocumentoEdital().getNome(), "");
//        }
//        if (edital.getStatus() != null) {
//            this.status = new StatusPersonalizadoResponse(edital.getStatus(), modelMapper);
//        }
//        if (edital.getTipoEdital() != null) {
//            this.tipoEdital = new TipoEditalResponse(edital.getTipoEdital(), modelMapper);
//        }
//        if (edital.getCronograma() != null) {
//            this.cronograma = edital.getCronograma().stream()
//                    .map(d -> new DataEtapaResponse(d, modelMapper))
//                    .collect(Collectors.toList());
//        }
//        if (edital.getEtapas() != null) {
//            this.etapas = edital.getEtapas().stream()
//                    .map(e -> new EtapaResponse(e, modelMapper))
//                    .collect(Collectors.toList());
//        }
//        if (edital.getCampos() != null) {
//            this.campos = edital.getCampos().stream()
//                    .map(c -> new CampoPersonalizadoResponse(c, modelMapper))
//                    .collect(Collectors.toList());
//        }
//    }
//}