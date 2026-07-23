/*package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class TipoEditalResponse {
    private Long id;
    private String nome;
    private String descricao;
    private List<EtapaResponse> etapas;
    private List<CampoPersonalizadoResponse> campos;

    public TipoEditalResponse(TipoEdital tipoEdital, ModelMapper modelMapper) {
        modelMapper.map(tipoEdital, this);

        //Mapeamento manual das listas para garantir a conversão correta dos filhos
        if (tipoEdital.getEtapasModelo() != null) {
            this.etapas = tipoEdital.getEtapasModelo().stream()
                    .map(e -> new EtapaResponse(e, modelMapper))
                    .collect(Collectors.toList());
        }
        if (tipoEdital.getCamposModelo() != null) {
            this.campos = tipoEdital.getCamposModelo().stream()
                    .map(c -> new CampoPersonalizadoResponse(c, modelMapper))
                    .collect(Collectors.toList());
        }
    }
}

 */