//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.DataEtapa;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.NotFoundException; // ou crie DataEtapaNotFoundException
//import java.util.List;
//
//public interface DataEtapaService {
//    DataEtapa salvar(DataEtapa dataEtapa);
//    DataEtapa buscar(Long id) throws NotFoundException;
//    List<DataEtapa> listarPorEdital(Long editalId);
//    DataEtapa editar(Long id, DataEtapa dataEtapa) throws NotFoundException;
//    void deletar(Long id) throws NotFoundException;
//}