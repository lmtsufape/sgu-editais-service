package br.edu.ufape.sguEditaisService.exceptions;

import br.edu.ufape.sguEditaisService.exceptions.business.AcessoNegadoException;
import br.edu.ufape.sguEditaisService.exceptions.business.InscricaoDuplicadaException;
import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // =========================================================
    // 1. ERROS DE VALIDAÇÃO DE PAYLOAD (Overrides do Spring)
    // =========================================================
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        // Extrai e formata os erros diretamente para a nossa lista de Strings
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiErrorResponse apiError = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Os dados enviados contêm erros de validação.",
                "Validation Error",
                path,
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // =========================================================
    // 2. ERROS DE RECURSO NÃO ENCONTRADO (O Agrupador 404)
    // =========================================================
    // Graças ao polimorfismo, este único método captura todas as suas 9 classes de NotFound
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), "Not Found", request.getRequestURI());
    }

    // =========================================================
    // 3. ERROS DE REGRA DE NEGÓCIO E CONFLITOS
    // =========================================================

    // Captura RegraNegocioException, PeriodoInvalidoException e MudancaStatusInvalidaException
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrorResponse> handleRegraNegocioException(RegraNegocioException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Bad Request", request.getRequestURI());
    }

    @ExceptionHandler(InscricaoDuplicadaException.class)
    public ResponseEntity<ApiErrorResponse> handleInscricaoDuplicadaException(InscricaoDuplicadaException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), "Conflict", request.getRequestURI());
    }

    // Mantidos da sua versão original para cobrir validações genéricas do Java
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), "Conflict", request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Bad Request", request.getRequestURI());
    }

    // =========================================================
    // 4. ERROS DE SEGURANÇA E ACESSO
    // =========================================================
    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ApiErrorResponse> handleAcessoNegadoException(AcessoNegadoException ex, HttpServletRequest request) {
        return this.buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage(), "Forbidden", request.getRequestURI());
    }

    // =========================================================
    // MÉTODOS AUXILIARES E BUILDERS
    // =========================================================

    private ResponseEntity<ApiErrorResponse> buildResponseEntity(HttpStatus status, String message, String error, String path) {
        ApiErrorResponse response = buildErrorResponse(status, message, error, path, null);
        return new ResponseEntity<>(response, status);
    }

    private ApiErrorResponse buildErrorResponse(HttpStatus status, String message, String error, String path, List<String> validationErrors) {
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .path(path)
                .message(message)
                .error(error)
                .validationErrors(validationErrors) // O Jackson ignorará automaticamente se for null
                .build();
    }
}