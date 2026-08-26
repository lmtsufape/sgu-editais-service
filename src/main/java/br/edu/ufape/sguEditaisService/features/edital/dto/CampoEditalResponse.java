package br.edu.ufape.sguEditaisService.features.edital.dto;

import br.edu.ufape.sguEditaisService.features.edital.campoedital.CampoEdital;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;

public record CampoEditalResponse(
        Long id,
        String titulo,
        TipoCampo tipoCampo,
        boolean obrigatorio,
        boolean herdadoDoModelo,
        String configuracoes
) {
    public static CampoEditalResponse from(CampoEdital campo) {
        return new CampoEditalResponse(
                campo.getId(),
                campo.getTitulo(),
                campo.getTipoCampo(),
                campo.isObrigatorio(),
                campo.isHerdadoDoModelo(),
                campo.getConfiguracoes()
        );
    }
}