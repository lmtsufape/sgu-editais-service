//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.models.Documento;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
//
//public interface DocumentoService {
//    Documento salvar(Documento documento);
//    Documento buscar(Long id) throws DocumentoNotFoundException;
//    void deletar(Long id) throws DocumentoNotFoundException;
//}

package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.Documento;

public interface DocumentoService {
    Documento salvar(Documento documento);
    Documento buscar(Long id);
    void deletar(Long id);
}