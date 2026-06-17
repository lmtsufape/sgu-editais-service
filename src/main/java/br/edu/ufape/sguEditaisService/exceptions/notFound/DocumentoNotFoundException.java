package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class DocumentoNotFoundException extends ResourceNotFoundException {
    public DocumentoNotFoundException(Long id) { super("Documento", id); }
}