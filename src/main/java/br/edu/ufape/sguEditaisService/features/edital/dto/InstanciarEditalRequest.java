package br.edu.ufape.sguEditaisService.features.edital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstanciarEditalRequest(
        @NotNull(message = "O ID do modelo de edital é obrigatório.")
        Long tipoEditalId,

        @NotBlank(message = "O título do novo edital é obrigatório.")
        String titulo,

        Long cursoId // Opcional, dependendo da necessidade do serviço que fizer uso
) {}