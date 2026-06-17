package br.edu.ufape.sguEditaisService.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiErrorResponse", description = "Objeto de resposta para erros da API")
public record ApiErrorResponse(

        @Schema(description = "Timestamp da ocorrência do erro", example = "2025-01-01T00:00:00.000Z")
        LocalDateTime timestamp,

        @Schema(description = "Código de status HTTP", example = "400")
        int status,

        @Schema(description = "Caminho da URI que originou o erro", example = "/api/v1/editais")
        String path,

        @Schema(description = "Mensagem detalhada do erro (da regra de negócio ou validação)", example = "As inscrições para este edital já foram encerradas.")
        String message,

        @Schema(description = "Nome do erro HTTP", example = "Bad Request")
        String error,

        @Schema(description = "Lista de erros de validação de payload (se houver)", example = "[\"O título não pode estar em branco\", \"A data de início deve ser no futuro\"]")
        List<String> validationErrors
) {
}