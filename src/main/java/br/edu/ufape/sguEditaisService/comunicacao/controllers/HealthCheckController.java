package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Só pra dizer se a API está Ok")
public class HealthCheckController {
    @Operation(summary = "Health Check", description = "Efetua a request para informar se a API está Ok")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API OK",
                    content = @Content(schema = @Schema(description = "Status da API", example = "Ok")))
    })
    @GetMapping()
    public String health() {
        return "Ok";
    }
}
