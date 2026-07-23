/*package br.edu.ufape.sguEditaisService.features.tipoedital;

import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarTipoEditalRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.dto.TipoEditalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEditalService {

    private final TipoEditalRepository tipoEditalRepository;

    @Transactional
    public TipoEditalResponse criar(CriarTipoEditalRequest request) {
        if(tipoEditalRepository.existsByNomeIgnoreCaseAndModuloOrigem(request.nome(), request.moduloOrigem())) {
            throw new IllegalArgumentException("Já existe um tipo de edital com esse nome neste módulo.");
        }

        TipoEdital tipo = TipoEdital.criar(
                request.nome(),
                request.descricao(),
                request.moduloOrigem()
        );

        return TipoEditalResponse.from(tipoEditalRepository.save(tipo));
    }

    @Transactional
    public TipoEditalResponse finalizarModelo(Long id) {
        TipoEdital tipo = buscarEntidade(id);

        // A regra de negócio está blindada na entidade!
        tipo.finalizar();

        return TipoEditalResponse.from(tipoEditalRepository.save(tipo));
    }

    @Transactional(readOnly = true)
    public TipoEditalResponse buscarPorId(Long id) {
        try {
            TipoEdital tipo = buscarEntidade(id);
            return TipoEditalResponse.from(tipo);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar TipoEdital por ID: " + id, e);
        }
    }

    @Transactional(readOnly = true)
    public List<TipoEditalResponse> listar() {
        return tipoEditalRepository.findAll()
                .stream()
                .map(TipoEditalResponse::from)
                .toList();
    }

    @Transactional
    public void deletar(Long id) {
        TipoEdital tipo = buscarEntidade(id);
        tipoEditalRepository.delete(tipo);
    }

    // O pacote restrito (default) permite que os serviços de Etapa/Campo busquem o objeto original
    public TipoEdital buscarEntidade(Long id) {
        return tipoEditalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoEdital", id));
    }
}

 */