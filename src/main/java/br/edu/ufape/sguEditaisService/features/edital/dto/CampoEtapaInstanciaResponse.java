package br.edu.ufape.sguEditaisService.features.edital.dto;

import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.campoetapainstancia.CampoEtapaInstancia;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;

public record CampoEtapaInstanciaResponse(
        Long id,
        String titulo,
        TipoCampo tipoCampo,
        boolean obrigatorio,
        boolean herdadoDoModelo,
        String configuracoes
) {
    public static CampoEtapaInstanciaResponse from(CampoEtapaInstancia campo) {
        return new CampoEtapaInstanciaResponse(
                campo.getId(),
                campo.getTitulo(),
                campo.getTipoCampo(),
                campo.isObrigatorio(),
                campo.isHerdadoDoModelo(),
                campo.getConfiguracoes()
        );
    }
}