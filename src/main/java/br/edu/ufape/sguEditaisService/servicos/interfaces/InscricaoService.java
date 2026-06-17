//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.Inscricao;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
//import java.util.List;
//import java.util.UUID;
//
//public interface InscricaoService {
//    Inscricao salvar(Inscricao inscricao);
//    Inscricao buscar(Long id) throws InscricaoNotFoundException;
//    List<Inscricao> listarPorUsuario(UUID userId);
//    List<Inscricao> listarPorEdital(Long editalId);
//    Inscricao buscarPorProtocolo(String numeroProtocolo) throws InscricaoNotFoundException;
//    Inscricao editar(Long id, Inscricao inscricao) throws InscricaoNotFoundException;
//    void deletar(Long id) throws InscricaoNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import java.util.List;
import java.util.UUID;

public interface InscricaoService {
    Inscricao salvar(Inscricao inscricao);
    Inscricao buscar(Long id);
    Inscricao buscarPorProtocolo(String protocolo);
    List<Inscricao> listarPorUsuario(UUID userId);
    Inscricao editar(Long id, Inscricao atualizacoes);
    void deletar(Long id);
}