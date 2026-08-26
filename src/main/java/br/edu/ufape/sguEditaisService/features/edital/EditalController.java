package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.features.edital.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editais")
@RequiredArgsConstructor
public class EditalController {

    private final EditalService editalService;

    @PostMapping("/instanciar")
    public ResponseEntity<EditalResponse> instanciarEdital(@RequestBody @Valid InstanciarEditalRequest request) {
        EditalResponse response = editalService.instanciar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditalResponse> buscarPorId(@PathVariable Long id) {
        EditalResponse response = editalService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/etapas")
    public ResponseEntity<EditalResponse> adicionarEtapaExclusiva(
            @PathVariable Long id,
            @RequestBody @Valid CriarEtapaInstanciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editalService.adicionarEtapaExclusiva(id, request));
    }

    @PostMapping("/{id}/campos")
    public ResponseEntity<EditalResponse> adicionarCampoGlobalExclusivo(
            @PathVariable Long id,
            @RequestBody @Valid br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editalService.adicionarCampoGlobalExclusivo(id, request));
    }

    @PostMapping("/{id}/etapas/{etapaId}/campos")
    public ResponseEntity<EditalResponse> adicionarCampoNaEtapaExclusiva(
            @PathVariable Long id,
            @PathVariable Long etapaId,
            @RequestBody @Valid br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editalService.adicionarCampoNaEtapaExclusiva(id, etapaId, request));
    }

    @PatchMapping("/{id}/cronograma")
    public ResponseEntity<EditalResponse> salvarCronograma(
            @PathVariable Long id,
            @RequestBody @Valid SalvarCronogramaRequest request) {
        return ResponseEntity.ok(editalService.salvarCronograma(id, request));
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<EditalResponse> publicarEdital(@PathVariable Long id) {
        return ResponseEntity.ok(editalService.publicarEdital(id));
    }

    @GetMapping
    public ResponseEntity<List<EditalResponse>> listarTodos() {
        return ResponseEntity.ok(editalService.listarTodos());
    }

    @DeleteMapping("/{id}/etapas/{etapaId}")
    public ResponseEntity<EditalResponse> deletarEtapaExclusiva(
            @PathVariable Long id,
            @PathVariable Long etapaId) {
        return ResponseEntity.ok(editalService.deletarEtapaExclusiva(id, etapaId));
    }

    @DeleteMapping("/{id}/campos/{campoId}")
    public ResponseEntity<EditalResponse> deletarCampoGlobalExclusivo(
            @PathVariable Long id,
            @PathVariable Long campoId) {
        return ResponseEntity.ok(editalService.deletarCampoGlobalExclusivo(id, campoId));
    }

    @DeleteMapping("/{id}/etapas/{etapaId}/campos/{campoId}")
    public ResponseEntity<EditalResponse> deletarCampoNaEtapaExclusiva(
            @PathVariable Long id,
            @PathVariable Long etapaId,
            @PathVariable Long campoId) {
        return ResponseEntity.ok(editalService.deletarCampoNaEtapaExclusiva(id, etapaId, campoId));
    }

}