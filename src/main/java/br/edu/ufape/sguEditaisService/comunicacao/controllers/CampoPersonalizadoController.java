package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/campo-personalizado")
@Tag(name = "Campo Personalizado", description = "Campos personalizados dos Editais")
public class CampoPersonalizadoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Cadastrar Campo Personalizado", description = "Cadastra um novo campo personalizado.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o campo personalizado cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<CampoPersonalizadoResponse> salvar(@Valid @RequestBody CampoPersonalizadoRequest request) {
        CampoPersonalizado entity = request.convertToEntity(request, modelMapper);
        CampoPersonalizado salvo = fachada.salvarCampoPersonalizado(entity);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar Campo Personalizado por ID", description = "Edita o campo personalizado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Campo personalizado editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Campo personalizado não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<CampoPersonalizadoResponse> editar(@PathVariable Long id, @Valid @RequestBody CampoPersonalizadoRequest request) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado entity = request.convertToEntity(request, modelMapper);
        CampoPersonalizado atualizado = fachada.editarCampoPersonalizado(id, entity);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @PatchMapping("/{id}/toggle-obrigatorio")
    @Operation(summary = "Trocar propriedade de um campo personalizado por ID.", description = "Edita a propriedade \"obrigatorio\" (boolean) de um campo personalizado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Propriedade \"obrigatorio\" editada.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Campo personalizado não encontrado para toggle.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<CampoPersonalizadoResponse> toggleObrigatorio(@PathVariable Long id) {
        CampoPersonalizado atualizado = fachada.alternarObrigatoriedade(id);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Campo Personalizado por ID", description = "Retorna o Campo Personalizado pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o campo personalizado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Campo personalizado não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<CampoPersonalizadoResponse> buscar(@PathVariable Long id) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado entity = fachada.buscarPorIdCampoPersonalizado(id);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Campos Personalizados", description = "Lista todos os campos personalizados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos campos personalizados.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<CampoPersonalizadoResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<CampoPersonalizado> page = fachada.listarCampoPersonalizado(pageable);
        Page<CampoPersonalizadoResponse> response = page.map(c -> new CampoPersonalizadoResponse(c, modelMapper));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Campo Personalizado por ID", description = "Deleta o Campo Personalizado pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Campo Personalizado deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Campo Personalizado não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws CampoPersonalizadoNotFoundException {
        fachada.deletarCampoPersonalizado(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}