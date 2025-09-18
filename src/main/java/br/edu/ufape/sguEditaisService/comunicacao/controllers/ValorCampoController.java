package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valor-campo")
@Tag(name = "Valor Campo", description = "Responsável pelos valores de campo")//"Define o conteúdo de um campo personalizado")
public class ValorCampoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @Operation(summary = "Cadastrar Valor Campo", description = "Cadastra uma nova Valor Campo.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da Valor Campo cadastrada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<ValorCampoResponse> salvar(@Valid @RequestBody ValorCampoRequest request) {
        ValorCampo entity = request.convertToEntity(request, modelMapper);
        ValorCampo salvo = fachada.salvarValorCampo(entity);
        return new ResponseEntity<>(new ValorCampoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar Valor Campo por ID", description = "Edita a Valor Campo pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Valor Campo editada.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Valor Campo não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<ValorCampoResponse> editar(@PathVariable Long id, @Valid @RequestBody ValorCampoRequest request) throws ValorCampoNotFoundException {
        ValorCampo entity = request.convertToEntity(request, modelMapper);
        ValorCampo atualizado = fachada.editarValorCampo(id, entity);
        return new ResponseEntity<>(new ValorCampoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Valor Campo por ID", description = "Retorna a Valor Campo pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Valor Campo.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Valor Campo não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<ValorCampoResponse> buscar(@PathVariable Long id) throws ValorCampoNotFoundException {
        ValorCampo entity = fachada.buscarPorIdValorCampo(id);
        return new ResponseEntity<>(new ValorCampoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Valores de Campo", description = "Lista todas os Valor Campos de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos Valor Campos.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<ValorCampoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<ValorCampo> page = fachada.listarValorCampo().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<ValorCampoResponse> response = page.map(v -> new ValorCampoResponse(v, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Valor Campo por ID", description = "Deleta a Valor Campo pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Valor Campo deletada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Valor Campo não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws ValorCampoNotFoundException {
        fachada.deletarValorCampo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
