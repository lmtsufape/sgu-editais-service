package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaReorderRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Etapa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/etapa")
@Tag(name = "Etapa", description = "Gerencia Etapas de um Edital")
public class EtapaController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Cadastrar Etapa", description = "Cadastra uma nova etapa.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da etapa cadastrada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EtapaResponse> salvar(@Valid @RequestBody EtapaRequest request) {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa salvo = fachada.salvarEtapa(entity);
        return new ResponseEntity<>(new EtapaResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar Etapa por ID", description = "Edita a Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Etapa editada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Etapa não encontrada para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EtapaResponse> editar(@PathVariable Long id, @Valid @RequestBody EtapaRequest request) throws EtapaNotFoundException {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa atualizado = fachada.editarEtapa(id, entity);
        return new ResponseEntity<>(new EtapaResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @PatchMapping("/reorder")
    @Operation(summary = "Reorder de Etapas (???)", description = "Executa reorder das etapas.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Etapas reordenadas.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Etapas não encontradas para reorder.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> reordenarEtapas(@Valid @RequestBody EtapaReorderRequest request) {
        fachada.atualizarOrdemEtapas(request.getIdsEtapasEmOrdem());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Etapa por ID", description = "Retorna a Etapa pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Etapa.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Etapa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EtapaResponse> buscar(@PathVariable Long id) throws EtapaNotFoundException {
        Etapa entity = fachada.buscarPorIdEtapa(id);
        return new ResponseEntity<>(new EtapaResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Etapa", description = "Lista todos as Etapas de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada das Etapas.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<EtapaResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Etapa> page = fachada.listarEtapa(pageable);
        Page<EtapaResponse> response = page.map(e -> new EtapaResponse(e, modelMapper));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{etapaId}/campos")
    @Operation(summary = "Busca Campos de uma Etapa por ID", description = "Retorna uma lista de Campos através dos IDs de etapas.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista de campos.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Id de Etapa não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorEtapa(@PathVariable Long etapaId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorEtapa(etapaId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Etapa por ID", description = "Deleta a Etapa pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Etapa deletada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Etapa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EtapaNotFoundException {
        fachada.deletarEtapa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}