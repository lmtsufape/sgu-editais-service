package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class StatusPersonalizadoNotFoundException extends ResourceNotFoundException {
    public StatusPersonalizadoNotFoundException(Long id) { super("Status Personalizado", id); }
}