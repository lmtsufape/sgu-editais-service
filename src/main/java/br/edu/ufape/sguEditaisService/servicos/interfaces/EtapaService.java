//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.Etapa;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
//import java.util.List;
//
//public interface EtapaService {
//    Etapa salvar(Etapa etapa);
//    Etapa buscar(Long id) throws EtapaNotFoundException;
//    List<Etapa> listarPorTipoEdital(Long tipoEditalId);
//    Etapa editar(Long id, Etapa etapa) throws EtapaNotFoundException;
//    void deletar(Long id) throws EtapaNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.Etapa;
import java.util.List;

public interface EtapaService {
    Etapa salvar(Etapa etapa);
    Etapa buscar(Long id);
    List<Etapa> listarPorTemplate(Long tipoEditalId);
    Etapa editar(Long id, Etapa atualizacoes);
    void deletar(Long id);
}