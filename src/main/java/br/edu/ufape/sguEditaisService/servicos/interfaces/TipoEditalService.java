//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.TipoEdital;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
//import java.util.List;
//
//public interface TipoEditalService {
//    TipoEdital salvar(TipoEdital tipoEdital);
//    TipoEdital buscar(Long id) throws TipoEditalNotFoundException;
//    List<TipoEdital> listar();
//    TipoEdital editar(Long id, TipoEdital tipoEdital) throws TipoEditalNotFoundException;
//    void deletar(Long id) throws TipoEditalNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.TipoEdital;
import java.util.List;

public interface TipoEditalService {
    TipoEdital salvar(TipoEdital tipoEdital);
    TipoEdital buscar(Long id);
    List<TipoEdital> listar();
    TipoEdital editar(Long id, TipoEdital atualizacoes);
    void deletar(Long id);
}