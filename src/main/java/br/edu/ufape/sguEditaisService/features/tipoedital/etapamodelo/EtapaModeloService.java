/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEditalService;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto.CriarEtapaModeloRequest;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto.EtapaModeloResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtapaModeloService {

    private final EtapaModeloRepository etapaModeloRepository;
    private final TipoEditalService tipoEditalService;

    @Transactional
    public EtapaModeloResponse adicionarEtapa(Long tipoEditalId, CriarEtapaModeloRequest request) {
        // 1. Busca a raiz do agregado
        TipoEdital tipo = tipoEditalService.buscarEntidade(tipoEditalId);

        // 2. Proteção de Domínio: Lança erro se o modelo já estiver FINALIZADO
        tipo.checarPermissaoEdicao();

        // 3. Cria a Etapa
        EtapaModelo etapa = EtapaModelo.criar(
                request.nome(),
                request.descricao(),
                request.ordem(),
                request.dataInicio(),
                request.dataFim(),
                request.configuracoes()
        );

        // 4. Delega a vinculação para a própria entidade (Encapsulamento/DDD)
        tipo.adicionarEtapa(etapa);

        // 5. Salva a etapa fisicamente no banco
        return EtapaModeloResponse.from(etapaModeloRepository.save(etapa));
    }

    @Transactional(readOnly = true)
    public EtapaModeloResponse buscarPorId(Long tipoEditalId, Long etapaId) {
        EtapaModelo etapa = buscarEntidade(etapaId);
        validarPertencimento(tipoEditalId, etapa);
        return EtapaModeloResponse.from(etapa);
    }

    @Transactional(readOnly = true)
    public List<EtapaModeloResponse> listarPorTipoEdital(Long tipoEditalId) {
        // Valida se o tipo existe antes de buscar
        tipoEditalService.buscarEntidade(tipoEditalId);

        return etapaModeloRepository.findByTipoEditalIdOrderByOrdemAsc(tipoEditalId)
                .stream()
                .map(EtapaModeloResponse::from)
                .toList();
    }

    @Transactional
    public void deletar(Long tipoEditalId, Long etapaId) {
        TipoEdital tipo = tipoEditalService.buscarEntidade(tipoEditalId);

        // Lança erro se tentar deletar uma etapa de um modelo já FINALIZADO
        tipo.checarPermissaoEdicao();

        EtapaModelo etapa = buscarEntidade(etapaId);
        validarPertencimento(tipoEditalId, etapa);

        // Desvincula do pai para manter a integridade da memória
        tipo.removerEtapa(etapa);

        // Hard Delete, limpando o banco de dados do erro de rascunho
        etapaModeloRepository.delete(etapa);
    }

    // Método auxiliar (Pode ter modificador public/default para ser usado pelo Serviço de Campos depois)
    public EtapaModelo buscarEntidade(Long id) {
        return etapaModeloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EtapaModelo", id));
    }

    // Segurança para evitar que deletem a etapa de um modelo passando o ID de outro
    private void validarPertencimento(Long tipoEditalId, EtapaModelo etapa) {
        if (!etapa.getTipoEdital().getId().equals(tipoEditalId)) {
            throw new RegraNegocioException("Esta etapa não pertence ao modelo de edital informado.");
        }
    }
}

 */