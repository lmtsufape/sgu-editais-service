//package br.edu.ufape.sguEditaisService.fachada;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaUpdateRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EtapaEditalRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.AvaliacaoInscricaoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
//import br.edu.ufape.sguEditaisService.auth.AuthenticatedUserProvider;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
//import br.edu.ufape.sguEditaisService.exceptions.GlobalAccessDeniedException;
//import br.edu.ufape.sguEditaisService.exceptions.InscricaoDuplicadaException;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
//import br.edu.ufape.sguEditaisService.models.*;
//import br.edu.ufape.sguEditaisService.models.enums.SituacaoInscricao;
//import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.*;
//import feign.FeignException;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class Fachada {
//
//    private final ModelMapper modelMapper;
//
//    private final ArmazenamentoService armazenamentoService;
//    private final DataEtapaService dataEtapaService;
//    private final CampoPersonalizadoService campoPersonalizadoService;
//    private final DocumentoService documentoService;
//    private final EditalService editalService;
//    private final EtapaService etapaService;
//    private final HistoricoEtapaInscricaoService historicoEtapaInscricaoService;
//    private final InscricaoService inscricaoService;
//    private final TipoEditalService tipoEditalService;
//    private final ValorCampoService valorCampoService;
//    private final StatusPersonalizadoService statusPersonalizadoService;
//    private final AuthenticatedUserProvider authenticatedUserProvider;
//    private final AuthServiceHandler authServiceHandler;
//    private final GeradorEditalPdfService geradorEditalPdfService;
//
//    // ================== TIPO EDITAL (MODELOS) ================== //
//
//    @Transactional
//    public TipoEditalResponse criarTipoEditalCompleto(TipoEditalRequest request) {
//        // 1. Converte e Salva a "Planta Baixa" base
//        TipoEdital tipoEdital = request.converterParaEntidade(modelMapper);
//        tipoEdital = tipoEditalService.salvar(tipoEdital);
//
//        // 2. Associa e Salva as Etapas (se houverem)
//        if (request.getEtapas() != null) {
//            for (EtapaRequest etapaReq : request.getEtapas()) {
//                Etapa etapa = etapaReq.converterParaEntidade(modelMapper);
//                etapa.setTipoEdital(tipoEdital); // Faz o vínculo FK
//                etapaService.salvar(etapa);
//                tipoEdital.getEtapas().add(etapa); // Mantém a referência em memória para a Response
//            }
//        }
//
//        // 3. Associa e Salva os Campos do Formulário (se houverem)
//        if (request.getCampos() != null) {
//            for (CampoPersonalizadoRequest campoReq : request.getCampos()) {
//                CampoPersonalizado campo = campoReq.converterParaEntidade(modelMapper);
//                campo.setTipoEdital(tipoEdital); // Faz o vínculo FK
//                campoPersonalizadoService.salvar(campo);
//                tipoEdital.getCampos().add(campo); // Mantém a referência em memória para a Response
//            }
//        }
//
//        return new TipoEditalResponse(tipoEdital, modelMapper);
//    }
//
//    public TipoEditalResponse buscarTipoEditalCompleto(Long id) throws TipoEditalNotFoundException {
//        TipoEdital tipoEdital = tipoEditalService.buscar(id);
//        return new TipoEditalResponse(tipoEdital, modelMapper);
//    }
//
//    public List<TipoEditalResponse> listarTiposEdital() {
//        return tipoEditalService.listar().stream()
//                .map(tipo -> new TipoEditalResponse(tipo, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    public void deletarTipoEdital(Long id) throws TipoEditalNotFoundException {
//        // Graças ao CascadeType.ALL e OrphanRemoval=true na entidade,
//        // deletar o TipoEdital vai deletar automaticamente as Etapas e Campos vinculados!
//        tipoEditalService.deletar(id);
//    }
//
//    // ================== GESTÃO DE ETAPAS ================== //
//
//    @Transactional
//    public EtapaResponse adicionarEtapaAoModelo(Long tipoEditalId, EtapaRequest request) throws TipoEditalNotFoundException {
//        TipoEdital tipoEdital = tipoEditalService.buscar(tipoEditalId);
//
//        // AUTO-SHIFT: Empurra as etapas subsequentes para a frente
//        deslocarOrdensParaFrente(tipoEditalId, request.getOrdem());
//
//        Etapa novaEtapa = request.converterParaEntidade(modelMapper);
//        novaEtapa.setTipoEdital(tipoEdital);
//        novaEtapa = etapaService.salvar(novaEtapa);
//
//        return new EtapaResponse(novaEtapa, modelMapper);
//    }
//
//    @Transactional
//    public EtapaResponse editarEtapa(Long etapaId, EtapaRequest request) throws EtapaNotFoundException {
//        Etapa etapaExistente = etapaService.buscar(etapaId);
//        Long tipoEditalId = etapaExistente.getTipoEdital().getId();
//        Integer ordemAntiga = etapaExistente.getOrdem();
//        Integer novaOrdem = request.getOrdem();
//
//        // AUTO-SHIFT PARA EDIÇÃO: Se a ordem mudou, reorganizamos as outras
//        if (!ordemAntiga.equals(novaOrdem)) {
//            List<Etapa> todasAsEtapas = etapaService.listarPorTipoEdital(tipoEditalId);
//            for (Etapa e : todasAsEtapas) {
//                if (e.getId().equals(etapaId)) continue; // Ignora a própria etapa que estamos a editar
//
//                if (novaOrdem < ordemAntiga && e.getOrdem() >= novaOrdem && e.getOrdem() < ordemAntiga) {
//                    e.setOrdem(e.getOrdem() + 1); // Empurra para a frente
//                    etapaService.salvar(e);
//                } else if (novaOrdem > ordemAntiga && e.getOrdem() <= novaOrdem && e.getOrdem() > ordemAntiga) {
//                    e.setOrdem(e.getOrdem() - 1); // Puxa para trás
//                    etapaService.salvar(e);
//                }
//            }
//        }
//
//        // Atualiza os dados da etapa alvo
//        etapaExistente.setNome(request.getNome());
//        etapaExistente.setDescricao(request.getDescricao());
//        etapaExistente.setOrdem(novaOrdem);
//
//        return new EtapaResponse(etapaService.salvar(etapaExistente), modelMapper);
//    }
//
//    @Transactional
//    public void deletarEtapa(Long etapaId) throws EtapaNotFoundException {
//        Etapa etapaExistente = etapaService.buscar(etapaId);
//        Long tipoEditalId = etapaExistente.getTipoEdital().getId();
//        Integer ordemRemovida = etapaExistente.getOrdem();
//
//        etapaService.deletar(etapaId);
//
//        // AUTO-SHIFT REVERSO: Tapa o buraco deixado pela etapa apagada
//        List<Etapa> etapasRestantes = etapaService.listarPorTipoEdital(tipoEditalId);
//        for (Etapa e : etapasRestantes) {
//            if (e.getOrdem() > ordemRemovida) {
//                e.setOrdem(e.getOrdem() - 1);
//                etapaService.salvar(e);
//            }
//        }
//    }
//
//    private void deslocarOrdensParaFrente(Long tipoEditalId, Integer ordemAlvo) {
//        List<Etapa> etapasAtuais = etapaService.listarPorTipoEdital(tipoEditalId);
//        for (Etapa e : etapasAtuais) {
//            if (e.getOrdem() >= ordemAlvo) {
//                e.setOrdem(e.getOrdem() + 1);
//                etapaService.salvar(e);
//            }
//        }
//    }
//
//    // ================== GESTÃO DE CAMPOS PERSONALIZADOS ================== //
//
//    @Transactional
//    public CampoPersonalizadoResponse editarCampo(Long campoId, CampoPersonalizadoRequest request) throws CampoPersonalizadoNotFoundException {
//        // 1. O Maestro (Fachada) busca o campo existente
//        CampoPersonalizado campoExistente = campoPersonalizadoService.buscar(campoId);
//
//        // 2. Atualiza apenas os dados permitidos (não mexemos no tipoEditalId para não quebrar a integridade)
//        campoExistente.setTitulo(request.getTitulo());
//        campoExistente.setTipoCampo(request.getTipoCampo());
//        campoExistente.setObrigatorio(request.isObrigatorio());
//
//        // 3. Salva a alteração usando o serviço específico
//        CampoPersonalizado campoSalvo = campoPersonalizadoService.editar(campoId, campoExistente);
//
//        // 4. Retorna o DTO atualizado
//        return new CampoPersonalizadoResponse(campoSalvo, modelMapper);
//    }
//
//    @Transactional
//    public void deletarCampo(Long campoId) throws CampoPersonalizadoNotFoundException {
//        // Como os campos do formulário não dependem de uma "ordem" estrita para renderizar
//        // (o frontend geralmente os lista na ordem de criação ou alfabética),
//        // a deleção é direta, sem necessidade de reordenar nada.
//        campoPersonalizadoService.deletar(campoId);
//    }
//
//    @Transactional
//    public CampoPersonalizadoResponse adicionarCampoAoModelo(Long tipoEditalId, CampoPersonalizadoRequest request) throws TipoEditalNotFoundException {
//        TipoEdital tipoEdital = tipoEditalService.buscar(tipoEditalId);
//
//        CampoPersonalizado novoCampo = request.converterParaEntidade(modelMapper);
//        novoCampo.setTipoEdital(tipoEdital);
//
//        novoCampo = campoPersonalizadoService.salvar(novoCampo);
//
//        return new CampoPersonalizadoResponse(novoCampo, modelMapper);
//    }
//
//    // ================== STATUS PERSONALIZADO ================== //
//
//    @Transactional
//    public StatusPersonalizadoResponse criarStatusPersonalizado(StatusPersonalizadoRequest request) {
//        StatusPersonalizado status = request.converterParaEntidade(modelMapper);
//        status = statusPersonalizadoService.salvar(status);
//        return new StatusPersonalizadoResponse(status, modelMapper);
//    }
//
//    public StatusPersonalizadoResponse buscarStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
//        StatusPersonalizado status = statusPersonalizadoService.buscar(id);
//        return new StatusPersonalizadoResponse(status, modelMapper);
//    }
//
//    public List<StatusPersonalizadoResponse> listarStatusPersonalizados() {
//        return statusPersonalizadoService.listar().stream()
//                .map(status -> new StatusPersonalizadoResponse(status, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    public List<StatusPersonalizadoResponse> listarStatusPorTipo(TipoStatus tipoStatus) {
//        // Muito útil para o frontend preencher os selects: "Traga apenas os status de EDITAL"
//        return statusPersonalizadoService.listarPorTipo(tipoStatus).stream()
//                .map(status -> new StatusPersonalizadoResponse(status, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public StatusPersonalizadoResponse editarStatusPersonalizado(Long id, StatusPersonalizadoRequest request) throws StatusPersonalizadoNotFoundException {
//        StatusPersonalizado existente = statusPersonalizadoService.buscar(id);
//
//        existente.setNome(request.getNome());
//        existente.setDescricao(request.getDescricao());
//        existente.setTipoStatus(request.getTipoStatus());
//
//        StatusPersonalizado salvo = statusPersonalizadoService.editar(id, existente);
//        return new StatusPersonalizadoResponse(salvo, modelMapper);
//    }
//
//    @Transactional
//    public void deletarStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
//        // Atenção: Se algum Edital ou Inscrição estiver usando este status,
//        // o banco de dados lançará uma exceção de Violação de Chave Estrangeira (o que é o correto e seguro).
//        statusPersonalizadoService.deletar(id);
//    }
//
//    // ================== EDITAIS E CRONOGRAMA ================== //
//
//    @Transactional
//    public EditalResponse criarEdital(EditalRequest request) {
//
//        Edital edital = request.converterParaEntidade(modelMapper);
//        edital.setTipoEdital(tipoEditalService.buscar(request.getTipoEditalId()));
//        edital.setStatus(statusPersonalizadoService.buscar(request.getStatusId()));
//
//        // --- 1. VALIDAÇÃO DO CURSO NO AUTH SERVICE ANTES DE SALVAR ---
//        if (request.getCursoId() != null) {
//            try {
//                // Tenta buscar o curso para garantir que ele existe
//                authServiceHandler.buscarCursoPorId(request.getCursoId());
//                edital.setCursoId(request.getCursoId());
//            } catch (Exception e) {
//                // Se der 404 Not Found ou erro de comunicação, barramos a criação
//                throw new IllegalArgumentException("O ID do curso informado não existe ou não está acessível no Auth Service.");
//            }
//        }
//
//        // 2. Salva a casca para gerar o ID do Edital
//        edital = editalService.salvar(edital);
//
//        // 3. Processa o Snapshot das Etapas + Cronograma (Tudo junto!)
//        if (request.getEtapas() != null) {
//            for (EtapaEditalRequest etapaReq : request.getEtapas()) {
//
//                // A) Cria a Etapa física isolada
//                Etapa novaEtapa = new Etapa();
//                novaEtapa.setNome(etapaReq.getNome());
//                novaEtapa.setDescricao(etapaReq.getDescricao());
//                novaEtapa.setOrdem(etapaReq.getOrdem());
//                novaEtapa.setEdital(edital);
//                novaEtapa = etapaService.salvar(novaEtapa);
//                edital.getEtapas().add(novaEtapa);
//
//                // B) Cria a DataEtapa (Cronograma) já vinculada à nova etapa exata
//                DataEtapa dataEtapa = new DataEtapa();
//                dataEtapa.setDataInicio(etapaReq.getDataInicio());
//                dataEtapa.setDataFim(etapaReq.getDataFim());
//                dataEtapa.setEdital(edital);
//                dataEtapa.setEtapa(novaEtapa);
//                dataEtapa.setStatus(statusPersonalizadoService.buscar(etapaReq.getStatusId()));
//
//                dataEtapaService.salvar(dataEtapa);
//                edital.getCronograma().add(dataEtapa);
//            }
//        }
//
//        // 4. Processa o Snapshot dos Campos Personalizados
//        if (request.getCampos() != null) {
//            for (CampoPersonalizadoRequest campoReq : request.getCampos()) {
//                CampoPersonalizado novoCampo = campoReq.converterParaEntidade(modelMapper);
//                novoCampo.setEdital(edital);
//                campoPersonalizadoService.salvar(novoCampo);
//                edital.getCampos().add(novoCampo);
//            }
//        }
//
//        // 5. Gera o PDF Dinâmico com as regras definitivas deste Edital
//        Documento pdfGerado = geradorEditalPdfService.gerarESalvarPdf(edital);
//        edital.setDocumentoEdital(pdfGerado);
//        edital = editalService.salvar(edital);
//
//        // 6. Retorna a resposta já enriquecida
//        return enriquecerEditalComCurso(edital);
//    }
//
//    public EditalResponse buscarEdital(Long id) {
//        Edital edital = editalService.buscar(id);
//        return enriquecerEditalComCurso(edital);
//    }
//
//    public List<EditalResponse> listarEditais() {
//        return editalService.listar().stream()
//                .map(this::enriquecerEditalComCurso)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void deletarEdital(Long id) {
//        Edital edital = editalService.buscar(id);
//
//        // Limpeza de disco: Apaga o PDF físico se existir
//        if (edital.getDocumentoEdital() != null) {
//            armazenamentoService.apagarArquivoFisico(edital.getDocumentoEdital().getPath());
//        }
//
//        // O CascadeType.ALL apaga o Documento no banco, o Cronograma e as Inscrições!
//        editalService.deletar(id);
//    }
//
//    // ================== ADICIONAR NOVA ETAPA A EDITAL EXISTENTE ================== //
//
//    @Transactional
//    public EditalResponse adicionarEtapaAoEdital(Long editalId, EtapaEditalRequest request) {
//        Edital edital = editalService.buscar(editalId);
//
//        // 1. Cria a nova Etapa física (Snapshot)
//        Etapa novaEtapa = new Etapa();
//        novaEtapa.setNome(request.getNome());
//        novaEtapa.setDescricao(request.getDescricao());
//        novaEtapa.setOrdem(request.getOrdem());
//        novaEtapa.setEdital(edital);
//        novaEtapa = etapaService.salvar(novaEtapa);
//        edital.getEtapas().add(novaEtapa);
//
//        // 2. Cria a DataEtapa (Cronograma) para esta nova etapa
//        DataEtapa dataEtapa = new DataEtapa();
//        dataEtapa.setDataInicio(request.getDataInicio());
//        dataEtapa.setDataFim(request.getDataFim());
//        dataEtapa.setEdital(edital);
//        dataEtapa.setEtapa(novaEtapa);
//        dataEtapa.setStatus(statusPersonalizadoService.buscar(request.getStatusId()));
//
//        dataEtapaService.salvar(dataEtapa);
//        edital.getCronograma().add(dataEtapa);
//
//        // 3. Como o edital mudou estruturalmente, geramos um novo PDF atualizado!
//        Documento pdfAntigo = edital.getDocumentoEdital();
//        Documento pdfNovo = geradorEditalPdfService.gerarESalvarPdf(edital);
//        edital.setDocumentoEdital(pdfNovo);
//        editalService.salvar(edital);
//
//        // Limpa o PDF velho do disco para não acumular lixo
//        if (pdfAntigo != null) {
//            armazenamentoService.apagarArquivoFisico(pdfAntigo.getPath());
//        }
//
//        return new EditalResponse(edital, modelMapper);
//    }
//
//    // ================== INSCRIÇÃO E FLUXO DO CANDIDATO ================== //
//
//    @Transactional
//    public InscricaoResponse processarInscricao(InscricaoRequest request, MultipartFile[] arquivos, UUID userIdLogado) throws IOException {
//
//        // --- 1. TRAVA DE TEMPO MANTIDA INTACTA ---
//        Edital edital = editalService.buscar(request.getEditalId());
//        LocalDateTime agora = LocalDateTime.now();
//
//        if (agora.isBefore(edital.getDataInicio()) || agora.isAfter(edital.getDataFim())) {
//            throw new IllegalArgumentException("O edital não está no período de vigência geral.");
//        }
//
//        boolean inscricaoAberta = edital.getCronograma().stream()
//                .anyMatch(dataEtapa ->
//                        dataEtapa.getEtapa().getNome().toLowerCase().contains("inscriç") &&
//                                !agora.isBefore(dataEtapa.getDataInicio()) &&
//                                !agora.isAfter(dataEtapa.getDataFim())
//                );
//
//        if (!inscricaoAberta) {
//            throw new IllegalArgumentException("O período de inscrições para este edital está encerrado ou ainda não começou.");
//        }
//
//        // --- 2. BUSCA O STATUS DINÂMICO ---
//        StatusPersonalizado statusAtual = statusPersonalizadoService.buscar(request.getStatusId());
//
//        Inscricao inscricao;
//
//        if (request.getId() != null) {
//            inscricao = inscricaoService.buscar(request.getId());
//            inscricao.getValoresCampos().clear();
//        } else {
//            inscricao = new Inscricao();
//            inscricao.setUserId(userIdLogado);
//            inscricao.setEdital(edital);
//            inscricao.setDataInscricao(LocalDateTime.now()); // Regista a data de criação
//        }
//
//        // --- 3. APLICA O STATUS CORRETO ---
//        inscricao.setStatusPersonalizado(statusAtual);
//
//        // --- 4. PROCESSA ARQUIVOS E VALORES MANTIDOS ---
//        List<Documento> documentosSalvos = new ArrayList<>();
//        if (arquivos != null && arquivos.length > 0) {
//            documentosSalvos = armazenamentoService.salvarArquivo(arquivos);
//        }
//
//        for (ValorCampoRequest valorReq : request.getValores()) {
//            CampoPersonalizado campo = campoPersonalizadoService.buscar(valorReq.getCampoId());
//            String valorFinal = valorReq.getValor();
//
//            if (campo.getTipoCampo() == TipoCampo.ARQUIVO && valorFinal != null) {
//                for (Documento doc : documentosSalvos) {
//                    if (doc.getNome().contains(valorFinal)) {
//                        valorFinal = doc.getPath();
//                        break;
//                    }
//                }
//            }
//
//            ValorCampo valorCampo = new ValorCampo();
//            valorCampo.setValor(valorFinal);
//            valorCampo.setCampoPersonalizado(campo);
//            inscricao.adicionarValorCampo(valorCampo);
//        }
//
//        inscricao = inscricaoService.salvar(inscricao);
//
//        // --- 6. AUDITORIA (SEM ERROS DE NULL) ---
//        // Regista o histórico sempre que é uma inscrição nova OU se mudou de status
//        if (inscricao.getHistorico().isEmpty()) {
//            HistoricoEtapaInscricao historico = HistoricoEtapaInscricao.builder()
//                    .dataMudanca(LocalDateTime.now())
//                    .statusNovo(statusAtual) // AGORA TEM A GARANTIA DO OBJETO REAL DO BANCO!
//                    .responsavelId(userIdLogado)
//                    .parecer("Inscrição registada com o status: " + statusAtual.getNome())
//                    .build();
//
//            inscricao.adicionarHistorico(historico);
//            historicoEtapaInscricaoService.salvar(historico);
//        }
//
//        return new InscricaoResponse(inscricao, modelMapper);
//    }
//
//    public InscricaoResponse buscarInscricaoPorId(Long id) {
//        return new InscricaoResponse(inscricaoService.buscar(id), modelMapper);
//    }
//
//    public InscricaoResponse buscarInscricaoPorProtocolo(String protocolo) {
//        return new InscricaoResponse(inscricaoService.buscarPorProtocolo(protocolo), modelMapper);
//    }
//
//    public List<InscricaoResponse> listarInscricoesPorUsuario(UUID userId) {
//        return inscricaoService.listarPorUsuario(userId).stream()
//                .map(i -> new InscricaoResponse(i, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    public List<InscricaoResponse> listarInscricoesPorEdital(Long editalId) {
//        return inscricaoService.listarPorEdital(editalId).stream()
//                .map(i -> new InscricaoResponse(i, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    // ================== RETIFICAÇÃO DE EDITAL (PRORROGAÇÃO) ================== //
//
//    @Transactional
//    public DataEtapaResponse alterarDataDoCronograma(Long dataEtapaId, DataEtapaUpdateRequest request) throws Exception {
//        // 1. Busca o registro exato no cronograma
//        DataEtapa cronogramaExistente = dataEtapaService.buscar(dataEtapaId);
//
//        // 2. Atualiza os prazos
//        cronogramaExistente.setDataInicio(request.getDataInicio());
//        cronogramaExistente.setDataFim(request.getDataFim());
//
//        // 3. Salva a alteração
//        DataEtapa salvo = dataEtapaService.salvar(cronogramaExistente);
//
//        // chamar o gerador de PDF aqui para ele reescrever o Edital com as novas datas automaticamente!
//        geradorEditalPdfService.gerarESalvarPdf(salvo.getEdital());
//
//        return new DataEtapaResponse(salvo, modelMapper);
//    }
//
//    // ================== AVALIAÇÃO DA Inscrição ================== //
//
//    @Transactional
//    public InscricaoResponse avaliarInscricao(Long inscricaoId, AvaliacaoInscricaoRequest request, UUID avaliadorId) {
//
//        // 1. Segurança Máxima: Valida se o avaliador do Token realmente existe no Auth Service
//        UsuarioResponse avaliador = authServiceHandler.buscarUsuarioPorId(avaliadorId);
//        if (avaliador == null || avaliador.getId() == null) {
//            throw new SecurityException("Avaliador não encontrado ou inativo no sistema de autenticação.");
//        }
//
//        // 2. Busca a inscrição e o novo status
//        Inscricao inscricao = inscricaoService.buscar(inscricaoId);
//        StatusPersonalizado statusAnterior = inscricao.getStatusPersonalizado();
//        StatusPersonalizado statusNovo = statusPersonalizadoService.buscar(request.getNovoStatusId());
//
//        // 3. Cria o registro imutável de Auditoria (Histórico) usando a identidade validada
//        HistoricoEtapaInscricao historico = HistoricoEtapaInscricao.builder()
//                .dataMudanca(LocalDateTime.now())
//                .statusAnterior(statusAnterior)
//                .statusNovo(statusNovo)
//                .responsavelId(avaliador.getId()) // Identidade garantida pelo Token JWT + AuthService
//                .parecer(request.getParecer())
//                .inscricao(inscricao)
//                .build();
//
//        // 4. Salva o histórico
//        historico = historicoEtapaInscricaoService.salvar(historico);
//        inscricao.getHistorico().add(historico);
//
//        // 5. Efetiva a mudança de status do candidato
//        inscricao.setStatusPersonalizado(statusNovo);
//        inscricao = inscricaoService.salvar(inscricao);
//
//        return new InscricaoResponse(inscricao, modelMapper);
//    }
//
//    // =========================================================
//    // AUXILIAR DE ENRIQUECIMENTO (API COMPOSITION)
//    // =========================================================
//    private EditalResponse enriquecerEditalComCurso(Edital edital) {
//        EditalResponse response = new EditalResponse(edital, modelMapper);
//
//        if (edital.getCursoId() != null) {
//            try {
//                // Busca os dados ricos do curso e anexa à resposta
//                response.setCurso(authServiceHandler.buscarCursoPorId(edital.getCursoId()));
//            } catch (Exception e) {
//                // Resiliência: Se o Auth Service estiver fora do ar durante um GET,
//                // devolvemos o Edital sem quebrar a tela do usuário.
//                // O objeto "curso" no JSON ficará null temporariamente.
//            }
//        }
//
//        return response;
//    }
//}

package br.edu.ufape.sguEditaisService.fachada;

import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import br.edu.ufape.sguEditaisService.servicos.interfaces.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Fachada {

    private final TipoEditalService tipoEditalService;
    private final EditalService editalService;
    private final EtapaService etapaService;
    private final CampoPersonalizadoService campoPersonalizadoService;
    private final StatusPersonalizadoService statusPersonalizadoService;
    private final InscricaoService inscricaoService;
    private final ValorCampoService valorCampoService;
    private final HistoricoEtapaInscricaoService historicoEtapaInscricaoService;
    private final DocumentoService documentoService;

    // --------------- TIPO EDITAL (MODELOS / TEMPLATES) ----------------

    public TipoEdital buscarTipoEdital(Long id) {
        return tipoEditalService.buscar(id);
    }

    public List<TipoEdital> listarTiposEdital() {
        return tipoEditalService.listar();
    }

    public TipoEdital editarTipoEditalBase(Long id, TipoEdital atualizacoes) {
        return tipoEditalService.editar(id, atualizacoes);
    }

    public void deletarTipoEdital(Long id) {
        tipoEditalService.deletar(id);
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - ORQUESTRAÇÃO ROBUSTA
     * Receberá o payload completo do serviço cliente (ex: PRAE Service) contendo o molde,
     * a lista de etapas genéricas e a lista de campos de formulário genéricos, salvando tudo em cascata.
     */
    @Transactional
    public TipoEdital criarTipoEditalCompleto(Object requestPayload) {
        // A ser implementado
        return null;
    }


    // --------------- EDITAL (INSTÂNCIAS / PROCESSOS REAIS) ----------------

    public Edital buscarEdital(Long id) {
        return editalService.buscar(id);
    }

    public List<Edital> listarEditais() {
        return editalService.listar();
    }

    public Edital editarEditalBase(Long id, Edital atualizacoes) {
        return editalService.editar(id, atualizacoes);
    }

    public void deletarEdital(Long id) {
        editalService.deletar(id);
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - O MOTOR DE DEEP COPY
     * É aqui que a mágica acontece. O método vai buscar um TipoEdital, clonar suas configurações,
     * clonar todas as Etapas e CamposPersonalizados, atrelar a um novo Edital,
     * e setar o status do edital como PLANEJAMENTO.
     */
    @Transactional
    public Edital instanciarEditalAPartirDeModelo(Long tipoEditalId, String moduloOrigem, Long cursoId) {
        // A ser implementado: Algoritmo de clonagem de entidades
        return null;
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - MÁQUINA DE ESTADOS DO EDITAL
     * Valida se todas as etapas do Edital (que estava em PLANEJAMENTO) receberam datas de início e fim.
     * Se sim, avança o status para PUBLICADO (abrindo para inscrições).
     */
    @Transactional
    public Edital publicarEdital(Long editalId) {
        // A ser implementado: Validação de cronograma e transição de estado
        return null;
    }


    // --------------- ETAPAS E CAMPOS PERSONALIZADOS ----------------

    public Etapa buscarEtapa(Long id) {
        return etapaService.buscar(id);
    }

    public Etapa editarEtapa(Long id, Etapa atualizacoes) {
        return etapaService.editar(id, atualizacoes);
    }

    public void deletarEtapa(Long id) {
        etapaService.deletar(id);
    }

    public CampoPersonalizado buscarCampo(Long id) {
        return campoPersonalizadoService.buscar(id);
    }

    public CampoPersonalizado editarCampo(Long id, CampoPersonalizado atualizacoes) {
        return campoPersonalizadoService.editar(id, atualizacoes);
    }

    public void deletarCampo(Long id) {
        campoPersonalizadoService.deletar(id);
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - REORDENAÇÃO AUTOMÁTICA
     * Adicionar ou deletar uma etapa no meio de um processo exige deslocar o campo 'ordem'
     * de todas as etapas subsequentes para manter a linha do tempo íntegra.
     */
    @Transactional
    public Etapa adicionarEtapaReordenando(Long editalId, Etapa novaEtapa) {
        // A ser implementado: Lógica de Auto-Shift
        return null;
    }


    // --------------- INSCRIÇÃO E FLUXO DO CANDIDATO ----------------

    public Inscricao buscarInscricao(Long id) {
        return inscricaoService.buscar(id);
    }

    public Inscricao buscarInscricaoPorProtocolo(String protocolo) {
        return inscricaoService.buscarPorProtocolo(protocolo);
    }

    public List<Inscricao> listarInscricoesPorUsuario(UUID userId) {
        return inscricaoService.listarPorUsuario(userId);
    }

    public List<Inscricao> listarInscricoesPorEdital(Long editalId) {
        return inscricaoService.listarPorEdital(editalId);
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - SUBMISSÃO DINÂMICA
     * Recebe os dados de inscrição, valida se o edital está no período de "inscrições abertas",
     * processa os Valores dos Campos (baseado nas regras do JSONB de cada campo) e salva.
     */
    @Transactional
    public Inscricao processarInscricao(Object inscricaoPayload, UUID userIdLogado) {
        // A ser implementado: Validação de tempo, parsing de campos e persistência em lote
        return null;
    }

    /*
     * TODO: ATENÇÃO ESPECIAL - AVALIAÇÃO DE WORKFLOW INTEGRADO
     * Ponto nevrálgico do sistema. O serviço chamador repassa quem está avaliando (Roles/UUID).
     * O sistema checa no JSONB da Etapa se a Role tem permissão.
     * Avança o status e crava um registro imutável em HistoricoEtapaInscricao.
     */
    @Transactional
    public Inscricao avaliarEtapaInscricao(Long inscricaoId, Long etapaId, Object avaliacaoPayload, List<String> perfisAvaliador, UUID avaliadorId) {
        // A ser implementado: Checagem de permissão flexível e Trilha de Auditoria
        return null;
    }


    // --------------- STATUS PERSONALIZADO (DICIONÁRIO) ----------------

    public StatusPersonalizado criarStatus(StatusPersonalizado status) {
        return statusPersonalizadoService.salvar(status);
    }

    public StatusPersonalizado buscarStatus(Long id) {
        return statusPersonalizadoService.buscar(id);
    }

    public List<StatusPersonalizado> listarStatusPorTipo(TipoStatus tipo) {
        return statusPersonalizadoService.listarPorTipo(tipo);
    }

    public StatusPersonalizado editarStatus(Long id, StatusPersonalizado atualizacoes) {
        return statusPersonalizadoService.editar(id, atualizacoes);
    }

    public void deletarStatus(Long id) {
        statusPersonalizadoService.deletar(id);
    }


    // --------------- HISTÓRICO DE AUDITORIA ----------------

    // Nota: Histórico não possui Update nem Delete por exigência de auditoria
    public List<HistoricoEtapaInscricao> listarHistoricoInscricao(Long inscricaoId) {
        return historicoEtapaInscricaoService.listarPorInscricao(inscricaoId);
    }

    public HistoricoEtapaInscricao buscarDetalheHistorico(Long id) {
        return historicoEtapaInscricaoService.buscar(id);
    }

}