package br.edu.ufape.sguEditaisService.exceptions.business;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
}