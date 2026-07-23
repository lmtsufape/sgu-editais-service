package br.edu.ufape.sguEditaisService.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiValidationErrorResponse", description = "Objeto de resposta para erros de validação da API")
public record ApiValidationErrorResponse(
        @Schema(description = "Timestamp da ocorrência do erro", example = "2025-01-01T00:00:00.000Z")
        LocalDateTime timestamp,

        @Schema(description = "Código de status HTTP", example = "400")
        int status,

        @Schema(description = "Caminho da URI que originou o erro", example = "/tipo-edital")
        String path,

        @Schema(description = "Mapa de erros de validação (campo: mensagem)", example = "{\"tipoA\": \"não pode estar em branco\", \"tipoB\": \"não pode estar em branco\"}")
        Map<String, String> errors
) {
}
