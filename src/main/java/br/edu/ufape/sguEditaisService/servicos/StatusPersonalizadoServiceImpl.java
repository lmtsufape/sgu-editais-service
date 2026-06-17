//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.StatusPersonalizadoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class StatusPersonalizadoServiceImpl implements StatusPersonalizadoService {
//
//    private final StatusPersonalizadoRepository repository;
//
//    @Override
//    public StatusPersonalizado salvar(StatusPersonalizado status) {
//        return repository.save(status);
//    }
//
//    @Override
//    public StatusPersonalizado buscar(Long id) throws StatusPersonalizadoNotFoundException {
//        return repository.findById(id).orElseThrow(() -> new StatusPersonalizadoNotFoundException(id));
//    }
//
//    @Override
//    public List<StatusPersonalizado> listar() {
//        return repository.findAll();
//    }
//
//    @Override
//    public List<StatusPersonalizado> listarPorTipo(TipoStatus tipoStatus) {
//        return repository.findByTipoStatus(tipoStatus);
//    }
//
//    @Override
//    public StatusPersonalizado editar(Long id, StatusPersonalizado status) throws StatusPersonalizadoNotFoundException {
//        StatusPersonalizado existente = buscar(id);
//        existente.setNome(status.getNome());
//        existente.setDescricao(status.getDescricao());
//        // tipoStatus geralmente não se edita para não quebrar a integridade, mas se precisar, adicione aqui.
//        return repository.save(existente);
//    }
//
//    @Override
//    public void deletar(Long id) throws StatusPersonalizadoNotFoundException {
//        repository.delete(buscar(id));
//    }
//}

package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import br.edu.ufape.sguEditaisService.servicos.interfaces.StatusPersonalizadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusPersonalizadoServiceImpl implements StatusPersonalizadoService {

    private final StatusPersonalizadoRepository repository;

    @Override
    public StatusPersonalizado salvar(StatusPersonalizado status) {
        return repository.save(status);
    }

    @Override
    public StatusPersonalizado buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StatusPersonalizadoNotFoundException(id));
    }

    @Override
    public List<StatusPersonalizado> listar() {
        return repository.findAll();
    }

    @Override
    public List<StatusPersonalizado> listarPorTipo(TipoStatus tipoStatus) {
        return repository.findByTipoStatus(tipoStatus);
    }

    @Override
    public StatusPersonalizado editar(Long id, StatusPersonalizado atualizacoes) {
        StatusPersonalizado existente = buscar(id);

        existente.setNome(atualizacoes.getNome());
        existente.setDescricao(atualizacoes.getDescricao());
        existente.setTipoStatus(atualizacoes.getTipoStatus());

        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}