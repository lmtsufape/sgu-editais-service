//package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaRequest;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter @Setter @NoArgsConstructor
//public class EditalRequest {
//    @NotBlank(message = "O título do edital é obrigatório.")
//    private String titulo;
//
//    private Long cursoId; // Opcional, para editais vinculados a cursos do Auth Service
//
//    private LocalDateTime dataInicio;
//    private LocalDateTime dataFim;
//
//    private boolean ativo = true;
//
//    @NotNull(message = "O modelo (TipoEdital) é obrigatório.")
//    private Long tipoEditalId;
//
//    @NotNull(message = "O ID do status inicial do edital é obrigatório.")
//    private Long statusId;
//
//    @Valid
//    private List<DataEtapaRequest> cronograma = new ArrayList<>();
//
//    @Valid
//    private List<EtapaEditalRequest> etapas = new ArrayList<>();
//
//    @Valid
//    private List<CampoPersonalizadoRequest> campos = new ArrayList<>();
//
//    public Edital converterParaEntidade(ModelMapper modelMapper) {
//        return modelMapper.map(this, Edital.class);
//    }
//}