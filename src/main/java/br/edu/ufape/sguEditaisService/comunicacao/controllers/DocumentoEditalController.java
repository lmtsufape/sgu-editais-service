package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
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
@RequestMapping("/documento-edital")
@Tag(name = "Documento Edital", description = "Gerencia documentos de editais")
public class DocumentoEditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Documento", description = "Cadastra um novo documento de um edital.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o documento de um edital cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Edital ID não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoEditalResponse> salvar(@Valid @RequestBody DocumentoEditalRequest request) {
        DocumentoEdital entity = request.convertToEntity(request, modelMapper);
        DocumentoEdital salvo = fachada.salvarDocumentoEdital(entity);
        return new ResponseEntity<>(new DocumentoEditalResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Documento por ID", description = "Edita o documento de um edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Documento do edital editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Documento do um edital não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoEditalResponse> editar(@PathVariable Long id, @Valid @RequestBody DocumentoEditalRequest request) throws DocumentoEditalNotFoundException {
        DocumentoEdital entity = request.convertToEntity(request, modelMapper);
        DocumentoEdital atualizado = fachada.editarDocumentoEdital(id, entity);
        return new ResponseEntity<>(new DocumentoEditalResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Documento por ID", description = "Retorna o documento de um edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o documento de um edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Documento de edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<DocumentoEditalResponse> buscar(@PathVariable Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital entity = fachada.buscarPorIdDocumentoEdital(id);
        return new ResponseEntity<>(new DocumentoEditalResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Documentos", description = "Lista todos os documentos de um edital de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos documentos de um edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<DocumentoEditalResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<DocumentoEdital> page = fachada.listarDocumentoEdital(pageable);
        Page<DocumentoEditalResponse> response = page.map(d -> new DocumentoEditalResponse(d, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar documento de um edital por ID", description = "Deleta o documento de um editalpelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Documento do edital deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Documento não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DocumentoEditalNotFoundException {
        fachada.deletarDocumentoEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
