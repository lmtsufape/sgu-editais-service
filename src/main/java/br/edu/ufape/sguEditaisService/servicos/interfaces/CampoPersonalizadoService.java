//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
//import java.util.List;
//
//public interface CampoPersonalizadoService {
//    CampoPersonalizado salvar(CampoPersonalizado campo);
//    CampoPersonalizado buscar(Long id) throws CampoPersonalizadoNotFoundException;
//    List<CampoPersonalizado> listarPorTipoEdital(Long tipoEditalId);
//    CampoPersonalizado editar(Long id, CampoPersonalizado campo) throws CampoPersonalizadoNotFoundException;
//    void deletar(Long id) throws CampoPersonalizadoNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import java.util.List;

public interface CampoPersonalizadoService {
    CampoPersonalizado salvar(CampoPersonalizado campo);
    CampoPersonalizado buscar(Long id);
    List<CampoPersonalizado> listarPorTemplate(Long tipoEditalId);
    List<CampoPersonalizado> listarPorEdital(Long editalId);
    CampoPersonalizado editar(Long id, CampoPersonalizado atualizacoes);
    void deletar(Long id);
}