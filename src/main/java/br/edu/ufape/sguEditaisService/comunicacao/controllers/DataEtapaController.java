////package br.edu.ufape.sguEditaisService.comunicacao.controllers;
////
////import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaRequest;
////import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaResponse;
////import br.edu.ufape.sguEditaisService.fachada.Fachada;
////import br.edu.ufape.sguEditaisService.models.DataEtapa;
////import br.edu.ufape.sguEditaisService.models.Edital;
////import br.edu.ufape.sguEditaisService.models.Etapa;
////import lombok.RequiredArgsConstructor;
////import org.modelmapper.ModelMapper;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import jakarta.validation.Valid;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@RestController
////@RequestMapping("/data-etapa")
////@RequiredArgsConstructor
////public class DataEtapaController {
////
////    private final Fachada fachada;
////    private final ModelMapper modelMapper;
////
////    @PostMapping
////    public ResponseEntity<DataEtapaResponse> salvarDataEtapa(@Valid @RequestBody DataEtapaRequest request) {
////        DataEtapa dataEtapa = request.convertToEntity(modelMapper);
////        DataEtapa novaDataEtapa = fachada.salvarDataEtapa(dataEtapa);
////        return new ResponseEntity<>(new DataEtapaResponse(novaDataEtapa, modelMapper), HttpStatus.CREATED);
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<DataEtapaResponse> buscarDataEtapaPorId(@PathVariable Long id) {
////        DataEtapa dataEtapa = fachada.buscarDataEtapaPorId(id);
////        return ResponseEntity.ok(new DataEtapaResponse(dataEtapa, modelMapper));
////    }
////
////    @GetMapping
////    public ResponseEntity<List<DataEtapaResponse>> listarDatasEtapas() {
////        List<DataEtapaResponse> datasEtapas = fachada.listarDatasEtapas().stream()
////                .map(dataEtapa -> new DataEtapaResponse(dataEtapa, modelMapper))
////                .collect(Collectors.toList());
////        return ResponseEntity.ok(datasEtapas);
////    }
////
////    @PatchMapping("/{id}")
////    public ResponseEntity<DataEtapaResponse> editarDataEtapa(@PathVariable Long id, @Valid @RequestBody DataEtapaRequest request) {
////        DataEtapa dataEtapa = request.convertToEntity(modelMapper);
////        DataEtapa dataEtapaEditada = fachada.editarDataEtapa(id, dataEtapa);
////        return ResponseEntity.ok(new DataEtapaResponse(dataEtapaEditada, modelMapper));
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deletarDataEtapa(@PathVariable Long id) {
////        fachada.deletarDataEtapa(id);
////        return ResponseEntity.noContent().build();
////    }
////}
//
//package br.edu.ufape.sguEditaisService.comunicacao.controllers;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaUpdateRequest;
//import br.edu.ufape.sguEditaisService.fachada.Fachada;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/data-etapa")
//@RequiredArgsConstructor
//public class DataEtapaController {
//
//    private final Fachada fachada;
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<DataEtapaResponse> alterarData(
//            @PathVariable Long id,
//            @Valid @RequestBody DataEtapaUpdateRequest request) throws Exception {
//
//        return ResponseEntity.ok(fachada.alterarDataDoCronograma(id, request));
//    }
//}