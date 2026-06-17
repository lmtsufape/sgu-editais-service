package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class ValorCampoNotFoundException extends ResourceNotFoundException {
    public ValorCampoNotFoundException(Long id) { super("Valor do Campo", id); }
}