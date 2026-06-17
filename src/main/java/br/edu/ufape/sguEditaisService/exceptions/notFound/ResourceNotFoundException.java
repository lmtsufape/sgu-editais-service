package br.edu.ufape.sguEditaisService.exceptions.notFound;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, Long id) {
        super(String.format("%s com ID %d não foi encontrado(a).", entityName, id));
    }

    public ResourceNotFoundException(String entityName, String identificador) {
        super(String.format("%s com identificador '%s' não foi encontrado(a).", entityName, identificador));
    }
}