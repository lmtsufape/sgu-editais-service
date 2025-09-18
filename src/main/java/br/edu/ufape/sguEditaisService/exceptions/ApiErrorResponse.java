package br.edu.ufape.sguEditaisService.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiErrorResponse", description = "Objeto de resposta para erros da API")
public record ApiErrorResponse(
        @Schema(description = "Timestamp da ocorrência do erro", example = "2025-01-01T00:00:00.000Z")
        LocalDateTime timestamp,

        @Schema(description = "Código de status HTTP", example = "500")
        int status,

        @Schema(description = "Caminho da URI que originou o erro", example = "/tipo-edital")
        String path,

        @Schema(description = "Mensagem de erro principal (para erros genéricos)", example = "Internal Server Error")
        String message,

        @Schema(description = "Erro", example = "Internal Server Error")
        String error
) {
}
