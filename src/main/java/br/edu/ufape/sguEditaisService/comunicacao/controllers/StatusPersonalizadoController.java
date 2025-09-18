package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("status-personalizado")
@RequiredArgsConstructor
@Tag(name = "Status Personalizado", description = "Gerencia Status Personalizados")
public class StatusPersonalizadoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Cadastrar Status Personalizado", description = "Cadastra uma nova Status Personalizado.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da Status Personalizado cadastrada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<StatusPersonalizadoResponse> salvar(@Valid @RequestBody StatusPersonalizadoRequest request) {
        StatusPersonalizado statusPersonalizado = request.convertToEntity(request, modelMapper);
        StatusPersonalizado salvo = fachada.salvarStatusPersonalizado(statusPersonalizado);
        return new ResponseEntity<>(new StatusPersonalizadoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Status Personalizado por ID", description = "Retorna a Status Personalizado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Status Personalizado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Status Personalizado não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<StatusPersonalizadoResponse> buscarPorId(@PathVariable Long id) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado statusPersonalizado = fachada.buscarPorIdStatusPersonalizado(id);
        return ResponseEntity.ok(new StatusPersonalizadoResponse(statusPersonalizado, modelMapper));
    }

    @GetMapping
    @Operation(summary = "Listar Status Personalizados", description = "Lista todas os Status Personalizados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos Status Personalizados.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<StatusPersonalizadoResponse>> listar() {
        List<StatusPersonalizado> statusPersonalizados = fachada.listarStatusPersonalizado();
        List<StatusPersonalizadoResponse> responses = statusPersonalizados.stream()
                .map(status -> new StatusPersonalizadoResponse(status, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar Status Personalizado por ID", description = "Edita a Status Personalizado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Status Personalizado editada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Status Personalizado não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<StatusPersonalizadoResponse> editar(@PathVariable Long id, @Valid @RequestBody StatusPersonalizadoRequest request) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado entity = request.convertToEntity(request, modelMapper);
        StatusPersonalizado atualizado = fachada.editarStatusPersonalizado(id, entity);
        return ResponseEntity.ok(new StatusPersonalizadoResponse(atualizado, modelMapper));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Status Personalizado por ID", description = "Deleta a Status Personalizado pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Status Personalizado deletada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Status Personalizado não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws StatusPersonalizadoNotFoundException {
        fachada.deletarStatusPersonalizado(id);
        return ResponseEntity.noContent().build();
    }
}