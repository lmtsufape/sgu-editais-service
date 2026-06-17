package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EditalNotFoundException extends ResourceNotFoundException {
    public EditalNotFoundException(Long id) { super("Edital", id); }
}
