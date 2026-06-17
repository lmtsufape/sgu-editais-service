//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.DataEtapaRepository;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.NotFoundException;
//import br.edu.ufape.sguEditaisService.models.DataEtapa;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.DataEtapaService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class DataEtapaServiceImpl implements DataEtapaService {
//
//    private final DataEtapaRepository repository;
//
//    @Override
//    public DataEtapa salvar(DataEtapa dataEtapa) {
//        return repository.save(dataEtapa);
//    }
//
//    @Override
//    public DataEtapa buscar(Long id) throws NotFoundException {
//        return repository.findById(id).orElseThrow(() -> new NotFoundException("DataEtapa não encontrada"));
//    }
//
//    @Override
//    public List<DataEtapa> listarPorEdital(Long editalId) {
//        return repository.findByEditalIdOrderByDataInicioAsc(editalId);
//    }
//
//    @Override
//    public DataEtapa editar(Long id, DataEtapa dataEtapa) throws NotFoundException {
//        DataEtapa existente = buscar(id);
//        existente.setDataInicio(dataEtapa.getDataInicio());
//        existente.setDataFim(dataEtapa.getDataFim());
//        existente.setStatus(dataEtapa.getStatus());
//        return repository.save(existente);
//    }
//
//    @Override
//    public void deletar(Long id) throws NotFoundException {
//        repository.delete(buscar(id));
//    }
//}