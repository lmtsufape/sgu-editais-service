/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.EtapaModelo;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.EtapaModeloService;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.dto.CampoEtapaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampoEtapaService {

    private final CampoEtapaRepository repository;
    private final EtapaModeloService etapaModeloService;

    @Transactional
    public CampoEtapaResponse adicionarCampo(Long tipoEditalId, Long etapaId, CriarCampoRequest request) {
        EtapaModelo etapa = etapaModeloService.buscarEntidade(etapaId);
        validarPertencimentoAoTipo(tipoEditalId, etapa);

        // Proteção: Verificamos a raiz através do relacionamento da etapa
        etapa.getTipoEdital().checarPermissaoEdicao();

        CampoEtapa campo = CampoEtapa.criar(
                request.titulo(), request.tipoCampo(), request.obrigatorio(), request.configuracoes()
        );

        etapa.adicionarCampo(campo);
        return CampoEtapaResponse.from(repository.save(campo));
    }

    @Transactional(readOnly = true)
    public List<CampoEtapaResponse> listarPorEtapa(Long tipoEditalId, Long etapaId) {
        EtapaModelo etapa = etapaModeloService.buscarEntidade(etapaId);
        validarPertencimentoAoTipo(tipoEditalId, etapa);

        return repository.findByEtapaModeloId(etapaId).stream()
                .map(CampoEtapaResponse::from).toList();
    }

    @Transactional
    public void deletar(Long tipoEditalId, Long etapaId, Long campoId) {
        EtapaModelo etapa = etapaModeloService.buscarEntidade(etapaId);
        validarPertencimentoAoTipo(tipoEditalId, etapa);
        etapa.getTipoEdital().checarPermissaoEdicao();

        CampoEtapa campo = repository.findById(campoId)
                .orElseThrow(() -> new ResourceNotFoundException("CampoEtapa", campoId));

        if (!campo.getEtapaModelo().getId().equals(etapaId)) {
            throw new RegraNegocioException("Campo não pertence a esta Etapa.");
        }

        etapa.removerCampo(campo);
        repository.delete(campo); // Hard Delete
    }

    private void validarPertencimentoAoTipo(Long tipoEditalId, EtapaModelo etapa) {
        if (!etapa.getTipoEdital().getId().equals(tipoEditalId)) {
            throw new RegraNegocioException("Etapa não pertence a este Modelo.");
        }
    }
}

 */