/*package br.edu.ufape.sguEditaisService.features.tipoedital;

import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarTipoEditalRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.dto.TipoEditalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelo")
@RequiredArgsConstructor
public class TipoEditalController {

    private final TipoEditalService tipoEditalService;


      //Passo 1: Cria a raiz do modelo (Nasce com o status RASCUNHO).

    @PostMapping
    public ResponseEntity<TipoEditalResponse> criar(@RequestBody @Valid CriarTipoEditalRequest request) {
        TipoEditalResponse response = tipoEditalService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


      //Passo Final: Valida se o modelo possui etapas e o trava como FINALIZADO.

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<TipoEditalResponse> finalizarModelo(@PathVariable Long id) {
        TipoEditalResponse response = tipoEditalService.finalizarModelo(id);
        return ResponseEntity.ok(response);
    }


      //Busca um modelo específico com toda a sua árvore (Etapas e Campos).

    @GetMapping("/{id}")
    public ResponseEntity<TipoEditalResponse> buscarPorId(@PathVariable Long id) {
        TipoEditalResponse response = tipoEditalService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }


      //Lista todos os modelos ativos (O Soft Delete já filtra os inativos automaticamente).

    @GetMapping
    public ResponseEntity<List<TipoEditalResponse>> listar() {
        List<TipoEditalResponse> response = tipoEditalService.listar();
        return ResponseEntity.ok(response);
    }


     // Realiza o Soft Delete do modelo (Inativa o modelo sem apagar o histórico).

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tipoEditalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
*/