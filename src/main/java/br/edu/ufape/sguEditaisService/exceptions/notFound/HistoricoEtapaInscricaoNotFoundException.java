package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class HistoricoEtapaInscricaoNotFoundException extends ResourceNotFoundException {
    public HistoricoEtapaInscricaoNotFoundException(Long id) {
        super("Histórico de Etapa de Inscrição", id);
    }
}