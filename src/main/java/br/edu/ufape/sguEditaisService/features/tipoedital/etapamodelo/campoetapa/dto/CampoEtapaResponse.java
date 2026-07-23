/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.dto;

import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.CampoEtapa;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;

public record CampoEtapaResponse(
        Long id,
        String titulo,
        TipoCampo tipoCampo,
        boolean obrigatorio,
        String configuracoes,
        Long etapaModeloId
) {
    public static CampoEtapaResponse from(CampoEtapa campo) {
        return new CampoEtapaResponse(
                campo.getId(),
                campo.getTitulo(),
                campo.getTipoCampo(),
                campo.isObrigatorio(),
                campo.getConfiguracoes(),
                campo.getEtapaModelo().getId()
        );
    }
}

 */