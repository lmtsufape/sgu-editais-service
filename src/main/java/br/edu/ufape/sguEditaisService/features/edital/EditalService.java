package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.edital.campoedital.CampoEdital;
import br.edu.ufape.sguEditaisService.features.edital.dto.*;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.campoetapainstancia.CampoEtapaInstancia;
import br.edu.ufape.sguEditaisService.features.tipoedital.EstadoModelo;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEditalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EditalService {

    private final EditalRepository editalRepository;
    private final TipoEditalService tipoEditalService;

    @Transactional
    public EditalResponse instanciar(InstanciarEditalRequest request) {
        // 1. Busca a Planta Baixa (Molde)
        TipoEdital molde = tipoEditalService.buscarEntidade(request.tipoEditalId());

        // 2. Regra de Negócio: Só instancia se o molde estiver FINALIZADO
        if (molde.getEstado() != EstadoModelo.FINALIZADO) {
            throw new RegraNegocioException("Só é possível instanciar um edital a partir de um modelo que já foi FINALIZADO.");
        }

        // 3. Cria a Instância (Edital Real)
        Edital editalInstanciado = Edital.instanciarDe(molde, request.titulo(), request.cursoId());

        // 4. DEEP COPY: Clonar os Campos Globais
        molde.getCamposModelo().forEach(campoMolde -> {
            CampoEdital campoClone = CampoEdital.clonarDe(
                    campoMolde.getTitulo(),
                    campoMolde.getTipoCampo(),
                    campoMolde.isObrigatorio(),
                    campoMolde.getConfiguracoes()
            );
            campoClone.marcarComoHerdado();
            editalInstanciado.adicionarCampoGlobal(campoClone);
        });

        // 5. DEEP COPY: Clonar as Etapas e seus respectivos Campos
        molde.getEtapasModelo().forEach(etapaMolde -> {
            EtapaInstancia etapaClone = EtapaInstancia.clonarDe(
                    etapaMolde.getNome(),
                    etapaMolde.getDescricao(),
                    etapaMolde.getOrdem(),
                    etapaMolde.getConfiguracoes()
            );
            etapaClone.marcarComoHerdado();

            // Laço interno: clonar os campos específicos desta etapa
            etapaMolde.getCamposEtapa().forEach(campoEtapaMolde -> {
                CampoEtapaInstancia campoEtapaClone = CampoEtapaInstancia.clonarDe(
                        campoEtapaMolde.getTitulo(),
                        campoEtapaMolde.getTipoCampo(),
                        campoEtapaMolde.isObrigatorio(),
                        campoEtapaMolde.getConfiguracoes()
                );
                campoEtapaClone.marcarComoHerdado();
                etapaClone.adicionarCampo(campoEtapaClone);
            });

            // Conecta a etapa clonada ao edital instanciado
            editalInstanciado.adicionarEtapa(etapaClone);
        });

        // 6. Graças ao CascadeType.ALL nas entidades "Edital" e "EtapaInstancia",
        // um único save no banco salva todas as dezenas/centenas de instâncias geradas de uma vez!
        Edital editalSalvo = editalRepository.save(editalInstanciado);

        return EditalResponse.from(editalSalvo);
    }

    public Edital buscarEntidade(Long id) {
        return editalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Edital", id));
    }

    @Transactional(readOnly = true)
    public EditalResponse buscarPorId(Long id) {
        Edital edital = buscarEntidade(id);
        return EditalResponse.from(edital);
    }

    @Transactional
    public EditalResponse adicionarEtapaExclusiva(Long editalId, CriarEtapaInstanciaRequest request) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        EtapaInstancia novaEtapa = EtapaInstancia.clonarDe(
                request.nome(), request.descricao(), request.ordem(), request.configuracoes()
        );

        edital.adicionarEtapa(novaEtapa);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse adicionarCampoGlobalExclusivo(Long editalId, br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest request) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        CampoEdital novoCampo = CampoEdital.clonarDe(
                request.titulo(), request.tipoCampo(), request.obrigatorio(), request.configuracoes()
        );

        edital.adicionarCampoGlobal(novoCampo);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse adicionarCampoNaEtapaExclusiva(Long editalId, Long etapaId, br.edu.ufape.sguEditaisService.features.tipoedital.dto.CriarCampoRequest request) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        EtapaInstancia etapa = edital.getEtapas().stream()
                .filter(e -> e.getId().equals(etapaId))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException("Etapa não encontrada neste edital."));

        CampoEtapaInstancia novoCampo = CampoEtapaInstancia.clonarDe(
                request.titulo(), request.tipoCampo(), request.obrigatorio(), request.configuracoes()
        );

        etapa.adicionarCampo(novoCampo);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse salvarCronograma(Long editalId, SalvarCronogramaRequest request) {
        Edital edital = buscarEntidade(editalId);

        List<Edital.PrazoEtapa> prazosDominio = request.prazos().stream()
                .map(p -> new Edital.PrazoEtapa(p.etapaId(), p.dataInicio(), p.dataFim()))
                .toList();

        edital.salvarCronograma(prazosDominio);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse publicarEdital(Long editalId) {
        Edital edital = buscarEntidade(editalId);
        edital.publicar(); // Valida se tudo está preenchido e muda o status
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional(readOnly = true)
    public List<EditalResponse> listarTodos() {
        return editalRepository.findAll()
                .stream()
                .map(EditalResponse::from)
                .toList();
    }

    @Transactional
    public EditalResponse deletarEtapaExclusiva(Long editalId, Long etapaId) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        EtapaInstancia etapa = edital.getEtapas().stream()
                .filter(e -> e.getId().equals(etapaId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("EtapaInstancia", etapaId));

        if (etapa.isHerdadoDoModelo()) {
            throw new RegraNegocioException("Não é permitido excluir uma etapa que faz parte da estrutura obrigatória do modelo.");
        }

        edital.getEtapas().remove(etapa);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse deletarCampoGlobalExclusivo(Long editalId, Long campoId) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        CampoEdital campo = edital.getCamposGlobais().stream()
                .filter(c -> c.getId().equals(campoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CampoEdital", campoId));

        if (campo.isHerdadoDoModelo()) {
            throw new RegraNegocioException("Não é permitido excluir um campo que faz parte da estrutura obrigatória do modelo.");
        }

        edital.getCamposGlobais().remove(campo);
        return EditalResponse.from(editalRepository.save(edital));
    }

    @Transactional
    public EditalResponse deletarCampoNaEtapaExclusiva(Long editalId, Long etapaId, Long campoId) {
        Edital edital = buscarEntidade(editalId);
        edital.checarPermissaoEdicao();

        EtapaInstancia etapa = edital.getEtapas().stream()
                .filter(e -> e.getId().equals(etapaId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("EtapaInstancia", etapaId));

        CampoEtapaInstancia campo = etapa.getCampos().stream()
                .filter(c -> c.getId().equals(campoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CampoEtapaInstancia", campoId));

        if (campo.isHerdadoDoModelo()) {
            throw new RegraNegocioException("Não é permitido excluir um campo que faz parte da estrutura obrigatória do modelo.");
        }

        etapa.getCampos().remove(campo);
        return EditalResponse.from(editalRepository.save(edital));
    }

}