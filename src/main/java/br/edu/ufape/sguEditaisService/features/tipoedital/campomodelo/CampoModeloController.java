/*package br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo;

import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo.dto.CampoModeloResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelo/{tipoId}/campos")
@RequiredArgsConstructor
public class CampoModeloController {

    private final CampoModeloService service;

    @PostMapping
    public ResponseEntity<CampoModeloResponse> adicionar(@PathVariable Long tipoId, @RequestBody @Valid CriarCampoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarCampo(tipoId, request));
    }

    @GetMapping
    public ResponseEntity<List<CampoModeloResponse>> listar(@PathVariable Long tipoId) {
        return ResponseEntity.ok(service.listarPorTipoEdital(tipoId));
    }

    @DeleteMapping("/{campoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long tipoId, @PathVariable Long campoId) {
        service.deletar(tipoId, campoId);
        return ResponseEntity.noContent().build();
    }
}

 */