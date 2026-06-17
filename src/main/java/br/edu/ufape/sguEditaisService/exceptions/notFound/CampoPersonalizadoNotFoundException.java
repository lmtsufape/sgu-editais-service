package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class CampoPersonalizadoNotFoundException extends ResourceNotFoundException {
    public CampoPersonalizadoNotFoundException(Long id) { super("Campo Personalizado", id); }
}