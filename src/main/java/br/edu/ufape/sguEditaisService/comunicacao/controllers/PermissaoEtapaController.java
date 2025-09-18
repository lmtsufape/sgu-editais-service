package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.PermissaoEtapaNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissao-etapa")
@Tag(name = "Permissão Etapa", description = "Gerencia permissões da etapa")
public class PermissaoEtapaController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Permissão Etapa", description = "Cadastra uma nova Permissão Etapa.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da Permissão Etapa cadastrada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<PermissaoEtapaResponse> salvar(@Valid @RequestBody PermissaoEtapaRequest request) {
        PermissaoEtapa entity = request.convertToEntity(request, modelMapper);
        PermissaoEtapa salvo = fachada.salvarPermissaoEtapa(entity);
        return new ResponseEntity<>(new PermissaoEtapaResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Permissão Etapa por ID", description = "Edita a Permissão Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Permissão Etapa editada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Permissão Etapa não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<PermissaoEtapaResponse> editar(@PathVariable Long id, @Valid @RequestBody PermissaoEtapaRequest request) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa entity = request.convertToEntity(request, modelMapper);
        PermissaoEtapa atualizado = fachada.editarPermissaoEtapa(id, entity);
        return new ResponseEntity<>(new PermissaoEtapaResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Permissão Etapa por ID", description = "Retorna a Permissão Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Permissão Etapa.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Permissão Etapa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<PermissaoEtapaResponse> buscar(@PathVariable Long id) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa entity = fachada.buscarPorIdPermissaoEtapa(id);
        return new ResponseEntity<>(new PermissaoEtapaResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Permissões Etapa", description = "Lista todas as Permissões de Etapa de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada das Permissões de Etapa.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<PermissaoEtapaResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<PermissaoEtapa> page = fachada.listarPermissaoEtapa().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<PermissaoEtapaResponse> response = page.map(p -> new PermissaoEtapaResponse(p, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Permissão Etapa por ID", description = "Deleta a Permissão Etapa pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Permissão Etapa deletada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Permissão Etapa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws PermissaoEtapaNotFoundException {
        fachada.deletarPermissaoEtapa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
