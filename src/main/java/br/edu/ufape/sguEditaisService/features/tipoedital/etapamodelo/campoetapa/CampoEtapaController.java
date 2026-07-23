/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa;

import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.dto.CampoEtapaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelo/{tipoId}/etapas/{etapaId}/campos")
@RequiredArgsConstructor
public class CampoEtapaController {

    private final CampoEtapaService service;

    @PostMapping
    public ResponseEntity<CampoEtapaResponse> adicionar(
            @PathVariable Long tipoId, @PathVariable Long etapaId, @RequestBody @Valid CriarCampoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarCampo(tipoId, etapaId, request));
    }

    @GetMapping
    public ResponseEntity<List<CampoEtapaResponse>> listar(@PathVariable Long tipoId, @PathVariable Long etapaId) {
        return ResponseEntity.ok(service.listarPorEtapa(tipoId, etapaId));
    }

    @DeleteMapping("/{campoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long tipoId, @PathVariable Long etapaId, @PathVariable Long campoId) {
        service.deletar(tipoId, etapaId, campoId);
        return ResponseEntity.noContent().build();
    }
}

 */