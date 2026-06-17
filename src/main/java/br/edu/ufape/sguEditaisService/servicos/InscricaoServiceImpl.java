//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.InscricaoRepository;
//import br.edu.ufape.sguEditaisService.exceptions.InscricaoDuplicadaException;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
//import br.edu.ufape.sguEditaisService.models.Inscricao;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.InscricaoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.Year;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class InscricaoServiceImpl implements InscricaoService {
//
//    private final InscricaoRepository repository;
//
//    @Override
//    public Inscricao salvar(Inscricao inscricao) {
//        // Validação de duplicidade para novas inscrições
//        if (inscricao.getId() == null && repository.existsByUserIdAndEditalId(inscricao.getUserId(), inscricao.getEdital().getId())) {
//            throw new InscricaoDuplicadaException();
//        }
//
//        // Lógica de Geração de Protocolo
//        // Como o Rascunho foi removido da arquitetura, se não tem protocolo, é uma inscrição nova!
//        if (inscricao.getNumeroProtocolo() == null) {
//            inscricao.setDataInscricao(LocalDateTime.now());
//            inscricao.setNumeroProtocolo(gerarProtocolo(inscricao));
//        }
//
//        return repository.save(inscricao);
//    }
//
//    @Override
//    public Inscricao buscar(Long id) throws InscricaoNotFoundException {
//        return repository.findById(id).orElseThrow(() -> new InscricaoNotFoundException(id));
//    }
//
//    @Override
//    public List<Inscricao> listarPorUsuario(UUID userId) {
//        return repository.findByUserId(userId);
//    }
//
//    @Override
//    public List<Inscricao> listarPorEdital(Long editalId) {
//        return repository.findByEditalId(editalId);
//    }
//
//    @Override
//    public Inscricao buscarPorProtocolo(String numeroProtocolo) throws InscricaoNotFoundException {
//        return repository.findByNumeroProtocolo(numeroProtocolo).orElseThrow();
//    }
//
//    @Override
//    public Inscricao editar(Long id, Inscricao inscricao) throws InscricaoNotFoundException {
//        // A edição crua não modifica o protocolo, apenas dados base. A orquestração é na Fachada.
//        Inscricao existente = buscar(id);
//
//        // Enum removido com sucesso. Agora a inscrição orbita apenas o StatusPersonalizado!
//        existente.setStatusPersonalizado(inscricao.getStatusPersonalizado());
//
//        return repository.save(existente);
//    }
//
//    @Override
//    public void deletar(Long id) throws InscricaoNotFoundException {
//        repository.delete(buscar(id));
//    }
//
//    private String gerarProtocolo(Inscricao inscricao) {
//        String ano = String.valueOf(Year.now().getValue());
//        String editalId = inscricao.getEdital() != null ? inscricao.getEdital().getId().toString() : "0";
//        String codigoUnico = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
//        return ano + "-ED" + editalId + "-" + codigoUnico;
//    }
//}

package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.InscricaoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import br.edu.ufape.sguEditaisService.servicos.interfaces.InscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InscricaoServiceImpl implements InscricaoService {

    private final InscricaoRepository repository;

    @Override
    public Inscricao salvar(Inscricao inscricao) {
        return repository.save(inscricao);
    }

    @Override
    public Inscricao buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InscricaoNotFoundException(id));
    }

    @Override
    public Inscricao buscarPorProtocolo(String protocolo) {
        return repository.findByNumeroProtocolo(protocolo)
                .orElseThrow(() -> new IllegalArgumentException("Protocolo não encontrado."));
    }

    @Override
    public List<Inscricao> listarPorUsuario(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Inscricao editar(Long id, Inscricao atualizacoes) {
        Inscricao existente = buscar(id);

        existente.setSituacao(atualizacoes.getSituacao());
        existente.setStatusPersonalizado(atualizacoes.getStatusPersonalizado());

        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        // Se aplicou o @SQLDelete na Inscrição, será exclusão lógica.
        // Caso não tenha aplicado (se preferir hard delete por conta da LGPD), executará exclusão física.
        repository.deleteById(id);
    }
}