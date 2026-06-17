//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.EditalRepository;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EditalServiceImpl implements EditalService {
//
//    private final EditalRepository repository;
//
//    @Override
//    public Edital salvar(Edital edital) {
//        return repository.save(edital);
//    }
//
//    @Override
//    public Edital buscar(Long id) throws EditalNotFoundException {
//        return repository.findById(id).orElseThrow(() -> new EditalNotFoundException(id));
//    }
//
//    @Override
//    public List<Edital> listar() {
//        return repository.findAll();
//    }
//
//    @Override
//    public Edital editar(Long id, Edital edital) throws EditalNotFoundException {
//        Edital existente = buscar(id);
//        existente.setTitulo(edital.getTitulo());
//        existente.setCursoId(edital.getCursoId());
//        existente.setDataInicio(edital.getDataInicio());
//        existente.setDataFim(edital.getDataFim());
//        existente.setAtivo(edital.isAtivo());
//        existente.setStatus(edital.getStatus());
//
//        // Documento, Cronograma e TipoEdital são gerenciados via Fachada para manter a integridade
//        return repository.save(existente);
//    }
//
//    @Override
//    public void deletar(Long id) throws EditalNotFoundException {
//        repository.delete(buscar(id));
//    }
//}

package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EditalServiceImpl implements EditalService {

    private final EditalRepository repository;

    @Override
    public Edital salvar(Edital edital) {
        return repository.save(edital);
    }

    @Override
    public Edital buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EditalNotFoundException(id));
    }

    @Override
    public List<Edital> listar() {
        return repository.findAll();
    }

    @Override
    public Edital editar(Long id, Edital atualizacoes) {
        Edital existente = buscar(id);

        existente.setTitulo(atualizacoes.getTitulo());
        existente.setCursoId(atualizacoes.getCursoId());
        existente.setDataInicio(atualizacoes.getDataInicio());
        existente.setDataFim(atualizacoes.getDataFim());
        existente.setStatus(atualizacoes.getStatus());
        // O módulo origem não deve ser alterado após a criação por segurança de domínio

        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}