package br.edu.ufape.sguEditaisService.exceptions;

import br.edu.ufape.sguEditaisService.exceptions.notFound.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiValidationErrorResponse apiError = new ApiValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ((org.springframework.web.context.request.ServletWebRequest)request).getRequest().getRequestURI(),
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return this.buildErrorResponse(HttpStatus.NOT_FOUND, request.getRequestURI(), ex.getMessage(), "Not Found");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        return this.buildErrorResponse(HttpStatus.CONFLICT, request.getRequestURI(), ex.getMessage(), "Conflict");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST, request.getRequestURI(), ex.getMessage(), "Bad Request");
    }

    @ExceptionHandler(InscricaoDuplicadaException.class)
    public ResponseEntity<ApiErrorResponse> handleInscricaoDuplicadaException(InscricaoDuplicadaException ex, HttpServletRequest request) {
        return this.buildErrorResponse(HttpStatus.CONFLICT, request.getRequestURI(), ex.getMessage(), "Conflict");
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String error, String message, String path) {
        ApiErrorResponse body = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                path,
                message,
                error
        );
        return new ResponseEntity<>(body, status);
    }
}
