//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
//import java.util.List;
//
//public interface StatusPersonalizadoService {
//    StatusPersonalizado salvar(StatusPersonalizado status);
//    StatusPersonalizado buscar(Long id) throws StatusPersonalizadoNotFoundException;
//    List<StatusPersonalizado> listar();
//    List<StatusPersonalizado> listarPorTipo(TipoStatus tipoStatus);
//    StatusPersonalizado editar(Long id, StatusPersonalizado status) throws StatusPersonalizadoNotFoundException;
//    void deletar(Long id) throws StatusPersonalizadoNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import java.util.List;

public interface StatusPersonalizadoService {
    StatusPersonalizado salvar(StatusPersonalizado status);
    StatusPersonalizado buscar(Long id);
    List<StatusPersonalizado> listar();
    List<StatusPersonalizado> listarPorTipo(TipoStatus tipoStatus);
    StatusPersonalizado editar(Long id, StatusPersonalizado atualizacoes);
    void deletar(Long id);
}