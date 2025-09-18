package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tipo-edital")
@Tag(name = "Tipo Edital", description = "É o edital \"template\". Serve de base para os outros editais.")
public class TipoEditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Cadastrar Tipo Edital", description = "Cadastra um novo tipo de edital \"Template\".")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o edital cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Unidade Administrativa não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<TipoEditalResponse> salvar(@Valid @RequestBody TipoEditalRequest request) {
        TipoEdital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.salvarTipoEdital(entity), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/duplicar")
    @Operation(summary = "Duplicar Tipo Edital", description = "Duplica um edital \"Template\".")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o edital duplicado.")
//    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo edital não encontrado para duplicação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<TipoEditalResponse> duplicarModelo(@PathVariable Long id) {
        return new ResponseEntity<>(fachada.duplicarTipoEdital(id), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar Tipo Edital por ID", description = "Edita o tipo de edital \"Template\" pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Tipo de edital editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo edital não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<TipoEditalResponse> editar(@PathVariable Long id, @Valid @RequestBody TipoEditalRequest request) throws TipoEditalNotFoundException {
        TipoEdital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.editarTipoEdital(id, entity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Tipo Edital por ID", description = "Retorna o tipo de edital \"Template\" pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o tipo de edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<TipoEditalResponse> buscar(@PathVariable Long id) throws TipoEditalNotFoundException {
        return new ResponseEntity<>(fachada.buscarPorIdTipoEdital(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Tipo Edital", description = "Lista todos os tipos de editais \"Template\" de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos tipos de edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<TipoEditalResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(fachada.listarTipoEdital(pageable));
    }

    @GetMapping("/{tipoEditalId}/etapas")
    @Operation(summary = "Listar Etapas de um Tipo Edital", description = "Lista todas as etapas de um tipo de edital \"Template\" pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista de etapas de um tipo de edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<EtapaResponse>> listarEtapasPorTipoEdital(@PathVariable Long tipoEditalId) {
        List<EtapaResponse> response = fachada.listarEtapasPorTipoEdital(tipoEditalId).stream()
                .map(etapa -> new EtapaResponse(etapa, modelMapper))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipoEditalId}/campos")
    @Operation(summary = "Listar Campos Personalizados de um Tipo Edital", description = "Lista todos os campos personalizados de um tipo de edital \"Template\" pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista de campos personalizados de um tipo de edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorTipoEdital(@PathVariable Long tipoEditalId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorTipoEdital(tipoEditalId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Tipo Edital por ID", description = "Deleta o tipo de edital \"Template\" pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Tipo de edital deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws TipoEditalNotFoundException {
        fachada.deletarTipoEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}