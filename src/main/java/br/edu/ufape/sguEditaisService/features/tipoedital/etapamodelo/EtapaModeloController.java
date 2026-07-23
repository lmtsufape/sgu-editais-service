/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo;

import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto.CriarEtapaModeloRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto.EtapaModeloResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelo/{tipoId}/etapas")
@RequiredArgsConstructor
public class EtapaModeloController {

    private final EtapaModeloService etapaModeloService;

    @PostMapping
    public ResponseEntity<EtapaModeloResponse> adicionarEtapa(
            @PathVariable Long tipoId,
            @RequestBody @Valid CriarEtapaModeloRequest request) {

        EtapaModeloResponse response = etapaModeloService.adicionarEtapa(tipoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EtapaModeloResponse>> listarEtapas(@PathVariable Long tipoId) {
        List<EtapaModeloResponse> response = etapaModeloService.listarPorTipoEdital(tipoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{etapaId}")
    public ResponseEntity<EtapaModeloResponse> buscarEtapaPorId(
            @PathVariable Long tipoId,
            @PathVariable Long etapaId) {

        EtapaModeloResponse response = etapaModeloService.buscarPorId(tipoId, etapaId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{etapaId}")
    public ResponseEntity<Void> deletarEtapa(
            @PathVariable Long tipoId,
            @PathVariable Long etapaId) {

        etapaModeloService.deletar(tipoId, etapaId);
        return ResponseEntity.noContent().build();
    }
}

 */