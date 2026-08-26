package br.edu.ufape.sguEditaisService.features.edital.dto;

import br.edu.ufape.sguEditaisService.features.edital.Edital;
import br.edu.ufape.sguEditaisService.features.edital.SituacaoEdital;

import java.util.List;

public record EditalResponse(
        Long id,
        String titulo,
        String moduloOrigem,
        Long cursoId,
        SituacaoEdital situacao,
        Long tipoEditalOrigemId,
        Long etapaVigenteId,
        List<EtapaInstanciaResponse> etapas,
        List<CampoEditalResponse> camposGlobais
) {
    public static EditalResponse from(Edital edital) {
        return new EditalResponse(
                edital.getId(),
                edital.getTitulo(),
                edital.getModuloOrigem(),
                edital.getCursoId(),
                edital.getSituacao(),
                edital.getTipoEditalOrigem().getId(),
                edital.obterEtapaVigente() != null ? edital.obterEtapaVigente().getId() : null,
                edital.getEtapas().stream().map(EtapaInstanciaResponse::from).toList(),
                edital.getCamposGlobais().stream().map(CampoEditalResponse::from).toList()
        );
    }
}