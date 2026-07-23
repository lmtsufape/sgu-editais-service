/*package br.edu.ufape.sguEditaisService.features.tipoedital.dto;

import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.EstadoModelo;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto.EtapaModeloResponse;
import br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo.dto.CampoModeloResponse;
import java.util.List;

public record TipoEditalResponse(
        Long id,
        String nome,
        String descricao,
        String moduloOrigem,
        EstadoModelo estado,
        List<EtapaModeloResponse> etapas, // Mapeia as etapas
        List<CampoModeloResponse> camposGerais // Mapeia os campos globais
) {
    public static TipoEditalResponse from(TipoEdital tipo) {
        return new TipoEditalResponse(
                tipo.getId(),
                tipo.getNome(),
                tipo.getDescricao(),
                tipo.getModuloOrigem(),
                tipo.getEstado(),
                tipo.getEtapasModelo().stream().map(EtapaModeloResponse::from).toList(),
                tipo.getCamposModelo().stream().map(CampoModeloResponse::from).toList()
        );
    }
}

 */