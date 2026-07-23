/*package br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEditalService;
import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo.dto.CampoModeloResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampoModeloService {

    private final CampoModeloRepository repository;
    private final TipoEditalService tipoEditalService;

    @Transactional
    public CampoModeloResponse adicionarCampo(Long tipoEditalId, CriarCampoRequest request) {
        TipoEdital tipo = tipoEditalService.buscarEntidade(tipoEditalId);
        tipo.checarPermissaoEdicao(); // Bloqueia se não for RASCUNHO

        CampoModelo campo = CampoModelo.criar(
                request.titulo(), request.tipoCampo(), request.obrigatorio(), request.configuracoes()
        );

        tipo.adicionarCampoModelo(campo);
        return CampoModeloResponse.from(repository.save(campo));
    }

    @Transactional(readOnly = true)
    public List<CampoModeloResponse> listarPorTipoEdital(Long tipoEditalId) {
        tipoEditalService.buscarEntidade(tipoEditalId);
        return repository.findByTipoEditalId(tipoEditalId).stream()
                .map(CampoModeloResponse::from).toList();
    }

    @Transactional
    public void deletar(Long tipoEditalId, Long campoId) {
        TipoEdital tipo = tipoEditalService.buscarEntidade(tipoEditalId);
        tipo.checarPermissaoEdicao();

        CampoModelo campo = repository.findById(campoId)
                .orElseThrow(() -> new ResourceNotFoundException("CampoModelo", campoId));

        if (!campo.getTipoEdital().getId().equals(tipoEditalId)) {
            throw new RegraNegocioException("Campo não pertence a este Modelo.");
        }

        tipo.removerCampoModelo(campo);
        repository.delete(campo); // Hard Delete
    }
}
*/