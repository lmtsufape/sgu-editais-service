package br.edu.ufape.sguEditaisService.features.edital.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PrazoEtapaRequest(
        @NotNull Long etapaId,
        @NotNull LocalDateTime dataInicio,
        @NotNull LocalDateTime dataFim
) {}