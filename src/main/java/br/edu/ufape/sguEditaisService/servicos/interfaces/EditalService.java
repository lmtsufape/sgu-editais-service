//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
//import java.util.List;
//
//public interface EditalService {
//    Edital salvar(Edital edital);
//    Edital buscar(Long id) throws EditalNotFoundException;
//    List<Edital> listar();
//    Edital editar(Long id, Edital edital) throws EditalNotFoundException;
//    void deletar(Long id) throws EditalNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.Edital;
import java.util.List;

public interface EditalService {
    Edital salvar(Edital edital);
    Edital buscar(Long id);
    List<Edital> listar();
    Edital editar(Long id, Edital atualizacoes);
    void deletar(Long id);
}