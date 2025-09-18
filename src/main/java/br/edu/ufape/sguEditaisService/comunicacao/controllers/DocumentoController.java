package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Documento;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/documento")
@Tag(name = "Documento", description = "Gerencia Documentos")
public class DocumentoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Documento", description = "Cadastra um novo documento.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações do documento cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoResponse> salvar(@Valid @RequestBody DocumentoRequest request) {
        Documento entity = request.convertToEntity(request, modelMapper);
        Documento salvo = fachada.salvarDocumento(entity);
        return new ResponseEntity<>(new DocumentoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Documento por ID", description = "Edita o documento pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Documento editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Etapa/Documento não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoResponse> editar(@PathVariable Long id, @Valid @RequestBody DocumentoRequest request) throws DocumentoNotFoundException {
        Documento entity = request.convertToEntity(request, modelMapper);
        Documento atualizado = fachada.editarDocumento(id, entity);
        return new ResponseEntity<>(new DocumentoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Documento por ID", description = "Retorna o documento pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o documento.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Documento não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoResponse> buscar(@PathVariable Long id) throws DocumentoNotFoundException {
        Documento entity = fachada.buscarPorIdDocumento(id);
        return new ResponseEntity<>(new DocumentoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Documento", description = "Lista todos os documentos de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos documentos.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<DocumentoResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Documento> page = fachada.listarDocumento().stream()
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<DocumentoResponse> response = page.map(d -> new DocumentoResponse(d, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Documento por ID", description = "Deleta o documento pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Documento deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Documento não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DocumentoNotFoundException {
        fachada.deletarDocumento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}