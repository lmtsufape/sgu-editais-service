package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarEtapaModeloRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @Min(1) Integer ordem,
        String configuracoes
) {}