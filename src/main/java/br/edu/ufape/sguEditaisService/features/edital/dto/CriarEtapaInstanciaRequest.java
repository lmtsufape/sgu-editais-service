package br.edu.ufape.sguEditaisService.features.edital.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarEtapaInstanciaRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @Min(1) Integer ordem,
        String configuracoes
) {}