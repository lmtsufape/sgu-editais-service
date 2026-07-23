/*package br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo;

import br.edu.ufape.sguEditaisService.features.tipoedital.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampoModelo extends CampoPersonalizado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_edital_id", nullable = false)
    private TipoEdital tipoEdital;

    private CampoModelo(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        super(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public static CampoModelo criar(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        return new CampoModelo(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public void vincularAoTipo(TipoEdital tipoEdital) {
        this.tipoEdital = tipoEdital;
    }

    public void desvincular() {
        this.tipoEdital = null;
    }
}

 */