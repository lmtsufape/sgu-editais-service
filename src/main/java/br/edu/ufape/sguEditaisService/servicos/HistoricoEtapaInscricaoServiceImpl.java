//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.HistoricoEtapaInscricaoRepository;
//import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.HistoricoEtapaInscricaoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class HistoricoEtapaInscricaoServiceImpl implements HistoricoEtapaInscricaoService {
//
//    private final HistoricoEtapaInscricaoRepository repository;
//
//    @Override
//    public HistoricoEtapaInscricao salvar(HistoricoEtapaInscricao historico) {
//        return repository.save(historico);
//    }
//
//    @Override
//    public List<HistoricoEtapaInscricao> listarPorInscricao(Long inscricaoId) {
//        return repository.findByInscricaoIdOrderByDataMudancaDesc(inscricaoId);
//    }
//}

package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.HistoricoEtapaInscricaoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.HistoricoEtapaInscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.servicos.interfaces.HistoricoEtapaInscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoEtapaInscricaoServiceImpl implements HistoricoEtapaInscricaoService {

    private final HistoricoEtapaInscricaoRepository repository;

    @Override
    public HistoricoEtapaInscricao salvar(HistoricoEtapaInscricao historico) {
        return repository.save(historico);
    }

    @Override
    public HistoricoEtapaInscricao buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new HistoricoEtapaInscricaoNotFoundException(id));
    }

    @Override
    public List<HistoricoEtapaInscricao> listarPorInscricao(Long inscricaoId) {
        // Trazendo do mais recente para o mais antigo, ideal para montar a timeline
        return repository.findByInscricaoIdOrderByDataMudancaDesc(inscricaoId);
    }
}