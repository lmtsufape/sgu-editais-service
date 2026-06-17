package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class TipoEditalNotFoundException extends ResourceNotFoundException {
    public TipoEditalNotFoundException(Long id) { super("Tipo de Edital", id); }
}