/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto;

import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.EtapaModelo;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa.dto.CampoEtapaResponse;
import java.time.LocalDateTime;
import java.util.List;

public record EtapaModeloResponse(
        Long id,
        String nome,
        String descricao,
        Integer ordem,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String configuracoes,
        List<CampoEtapaResponse> campos
) {
    public static EtapaModeloResponse from(EtapaModelo etapa) {
        return new EtapaModeloResponse(
                etapa.getId(),
                etapa.getNome(),
                etapa.getDescricao(),
                etapa.getOrdem(),
                etapa.getDataInicio(),
                etapa.getDataFim(),
                etapa.getConfiguracoes(),
                etapa.getCamposEtapa().stream().map(CampoEtapaResponse::from).toList()
        );
    }
}

 */