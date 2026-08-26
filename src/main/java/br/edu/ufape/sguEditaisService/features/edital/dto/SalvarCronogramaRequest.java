package br.edu.ufape.sguEditaisService.features.edital.dto;

import jakarta.validation.Valid;
import java.util.List;

public record SalvarCronogramaRequest(
        // Removemos o @NotEmpty para permitir que o usuário salve "zerado" se quiser limpar as datas
        @Valid List<PrazoEtapaRequest> prazos
) {}