package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EtapaNotFoundException extends ResourceNotFoundException {
    public EtapaNotFoundException(Long id) { super("Etapa", id); }
}