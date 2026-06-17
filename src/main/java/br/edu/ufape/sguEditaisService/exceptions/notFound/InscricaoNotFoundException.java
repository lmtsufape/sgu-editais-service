package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class InscricaoNotFoundException extends ResourceNotFoundException {
    public InscricaoNotFoundException(Long id) { super("Inscrição", id); }
    public InscricaoNotFoundException(String protocolo) { super("Inscrição", protocolo); }
}