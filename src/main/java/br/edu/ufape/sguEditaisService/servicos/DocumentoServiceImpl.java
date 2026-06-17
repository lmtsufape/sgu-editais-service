//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.dados.DocumentoRepository;
//import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
//import br.edu.ufape.sguEditaisService.models.Documento;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class DocumentoServiceImpl implements DocumentoService {
//
//    private final DocumentoRepository repository;
//
//    @Override
//    public Documento salvar(Documento documento) {
//        return repository.save(documento);
//    }
//
//    @Override
//    public Documento buscar(Long id) throws DocumentoNotFoundException {
//        return repository.findById(id).orElseThrow(() -> new DocumentoNotFoundException(id));
//    }
//
//    @Override
//    public void deletar(Long id) throws DocumentoNotFoundException {
//        repository.delete(buscar(id));
//    }
//}

package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Documento;
import br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;

    @Override
    public Documento salvar(Documento documento) {
        return repository.save(documento);
    }

    @Override
    public Documento buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DocumentoNotFoundException(id));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}