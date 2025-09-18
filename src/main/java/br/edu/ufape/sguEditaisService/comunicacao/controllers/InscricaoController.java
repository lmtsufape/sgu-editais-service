package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Inscricao;
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
@RequestMapping("/inscricao")
@Tag(name = "Inscrição", description = "Responsável pelas inscrições")
public class InscricaoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Inscrição", description = "Cadastra uma nova Inscrição.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da Inscrição cadastrada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflito.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<InscricaoResponse> salvar(@Valid @RequestBody InscricaoRequest request) {
        Inscricao entity = request.convertToEntity(request, modelMapper);
        Inscricao salvo = fachada.salvarInscricao(entity);
        return new ResponseEntity<>(new InscricaoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Inscrição por ID", description = "Edita a Inscrição pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Inscrição editada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Inscrição não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<InscricaoResponse> editar(@PathVariable Long id, @Valid @RequestBody InscricaoRequest request) throws InscricaoNotFoundException {
        Inscricao entity = request.convertToEntity(request, modelMapper);
        Inscricao atualizado = fachada.editarInscricao(id, entity);
        return new ResponseEntity<>(new InscricaoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Inscrição por ID", description = "Retorna a Inscrição pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Inscrição.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<InscricaoResponse> buscar(@PathVariable Long id) throws InscricaoNotFoundException {
        Inscricao entity = fachada.buscarPorIdInscricao(id);
        return new ResponseEntity<>(new InscricaoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Inscrições", description = "Lista todas as Inscrições de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada das Inscrições.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<InscricaoResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Inscricao> page = fachada.listarInscricao(pageable);
        Page<InscricaoResponse> response = page.map(i -> new InscricaoResponse(i, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Inscrição por ID", description = "Deleta a Inscrição pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Inscrição deletada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws InscricaoNotFoundException {
        fachada.deletarInscricao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
