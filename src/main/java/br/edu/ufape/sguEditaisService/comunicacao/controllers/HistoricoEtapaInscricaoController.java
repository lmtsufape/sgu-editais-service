package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.HistoricoEtapaInscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
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
@RequestMapping("/historico-etapa-inscricao")
@Tag(name = "Histórico Etapa Inscrição", description = "Gerencia o Histórico das Etapas de Inscrição")
public class HistoricoEtapaInscricaoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Histórico de Etapa", description = "Cadastra um novo histórico de etapa.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações do histórico de etapa cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<HistoricoEtapaInscricaoResponse> salvar(@Valid @RequestBody HistoricoEtapaInscricaoRequest request) {
        HistoricoEtapaInscricao entity = request.convertToEntity(request, modelMapper);
        HistoricoEtapaInscricao salvo = fachada.salvarHistoricoEtapaInscricao(entity);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Histórico de Etapa por ID", description = "Edita o Histórico de Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Histórico de Etapa editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Histórico de Etapa não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<HistoricoEtapaInscricaoResponse> editar(@PathVariable Long id, @Valid @RequestBody HistoricoEtapaInscricaoRequest request) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao entity = request.convertToEntity(request, modelMapper);
        HistoricoEtapaInscricao atualizado = fachada.editarHistoricoEtapaInscricao(id, entity);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Histórico de Etapa por ID", description = "Retorna o Histórico de Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o Histórico de Etapa.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Histórico de Etapa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<HistoricoEtapaInscricaoResponse> buscar(@PathVariable Long id) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao entity = fachada.buscarPorIdHistoricoEtapaInscricao(id);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Histórico de Etapas", description = "Lista todos os Histórico de Etapas de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos Históricos de Etapas.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<HistoricoEtapaInscricaoResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<HistoricoEtapaInscricao> page = fachada.listarHistoricoEtapaInscricao().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<HistoricoEtapaInscricaoResponse> response = page.map(h -> new HistoricoEtapaInscricaoResponse(h, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Histórico de Etapa por ID", description = "Deleta o Histórico de Etapa pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Histórico de Etapa deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Histórico de Etapa não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws HistoricoEtapaInscricaoNotFoundException {
        fachada.deletarHistoricoEtapaInscricao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}