package br.edu.ufape.sguEditaisService.features.edital.etapainstancia.campoetapainstancia;

import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;
import br.edu.ufape.sguEditaisService.features.tipoedital.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampoEtapaInstancia extends CampoPersonalizado {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_instancia_id", nullable = false)
    private EtapaInstancia etapaInstancia;

    @Column(nullable = false)
    private boolean herdadoDoModelo = false;

    private CampoEtapaInstancia(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        super(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public static CampoEtapaInstancia clonarDe(String titulo, TipoCampo tipoCampo, boolean obrigatorio, String configuracoes) {
        return new CampoEtapaInstancia(titulo, tipoCampo, obrigatorio, configuracoes);
    }

    public void vincularAEtapa(EtapaInstancia etapa) {
        this.etapaInstancia = etapa;
    }

    public void marcarComoHerdado() {
        this.herdadoDoModelo = true;
    }

}