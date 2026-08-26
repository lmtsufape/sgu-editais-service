package br.edu.ufape.sguEditaisService.features.edital.campoedital;

import br.edu.ufape.sguEditaisService.features.edital.Edital;
import br.edu.ufape.sguEditaisService.features.tipoedital.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampoEdital extends CampoPersonalizado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edital_id", nullable = false)
    private Edital edital;

    @Column(nullable = false)
    private boolean herdadoDoModelo = false;

    private CampoEdital(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        super(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public static CampoEdital clonarDe(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        return new CampoEdital(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public void vincularAoEdital(Edital edital) {
        this.edital = edital;
    }

    public void marcarComoHerdado() {
        this.herdadoDoModelo = true;
    }
}