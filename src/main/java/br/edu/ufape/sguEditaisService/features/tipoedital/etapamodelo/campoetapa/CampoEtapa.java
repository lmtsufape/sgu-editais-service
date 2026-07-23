/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.campoetapa;

import br.edu.ufape.sguEditaisService.features.tipoedital.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.EtapaModelo;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampoEtapa extends CampoPersonalizado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_modelo_id", nullable = false)
    private EtapaModelo etapaModelo;

    private CampoEtapa(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        super(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public static CampoEtapa criar(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        return new CampoEtapa(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public void vincularAEtapa(EtapaModelo etapaModelo) {
        this.etapaModelo = etapaModelo;
    }

    public void desvincular() {
        this.etapaModelo = null;
    }
}

 */