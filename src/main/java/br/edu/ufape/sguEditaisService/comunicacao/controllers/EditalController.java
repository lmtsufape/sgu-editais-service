package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.AtualizarStatusRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.TransformarEmModeloRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Edital;
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
@RequestMapping("/edital")
@Tag(name = "Edital", description = "É o edital propriamente dito.")
public class EditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Cadastrar Edital", description = "Cadastra um novo tipo de edital.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o edital cadastrado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EditalResponse> salvar(@Valid @RequestBody EditalRequest request) {
        Edital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.salvarEdital(entity), HttpStatus.CREATED);
    }

    @PostMapping("/from-template/{templateId}")
    @Operation(summary = "Cadastrar Edital a partir de um Template ID", description = "Cadastra um novo tipo de edital a partir de um Template ID.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o edital cadastrado a partir de um Template.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EditalResponse> criarEditalPorModelo(@PathVariable Long templateId, @Valid @RequestBody EditalRequest request) {
        Edital editalBase = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.criarEditalAPartirDeModelo(templateId, editalBase), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/transformar-em-modelo")
    @Operation(summary = "Cadastrar Tipo Edital a partir de um Edital ID", description = "Cadastra um novo tipo de edital a partir de um Edital ID.")
    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna o edital template criado a partir de um edital.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Tipo Edital ID não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<TipoEditalResponse> transformarEmModelo(@PathVariable Long id, @Valid @RequestBody TransformarEmModeloRequest request) {
        TipoEdital novoModelo = fachada.transformarEditalEmModelo(id, request.getNomeModelo(), request.getDescricaoModelo());
        return new ResponseEntity<>(new TipoEditalResponse(novoModelo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar Edital por ID", description = "Edita o edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Edital editado.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EditalResponse> editar(@PathVariable Long id, @Valid @RequestBody EditalRequest request) throws EditalNotFoundException {
        Edital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.editarEdital(id, entity), HttpStatus.OK);
    }

    @PatchMapping("/{id}/atualizar-status")
    @Operation(summary = "Atualiza Status de um Edital a partir de um Edital ID", description = "Cadastra um novo tipo de edital a partir de um Edital ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o edital editado com novo status.")
    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Parâmetros não encontrados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EditalResponse> atualizarStatus(@PathVariable Long id, @Valid @RequestBody AtualizarStatusRequest request) {
        return new ResponseEntity<>(fachada.atualizarStatusEdital(id, request.getStatusId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Edital por ID", description = "Retorna o tipo de edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna o edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EditalResponse> buscar(@PathVariable Long id) throws EditalNotFoundException {
        return new ResponseEntity<>(fachada.buscarPorIdEdital(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar Edital", description = "Lista todos os editais de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos editais.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<EditalResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(fachada.listarEdital(pageable));
    }

    @GetMapping("/publicados")
    @Operation(summary = "Listar Editais Publicados", description = "Lista todos os editais de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada dos editais publicados.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Page<EditalResponse>> listarPublicados(@ParameterObject @PageableDefault(sort = "fimIncricao") Pageable pageable) {
        return ResponseEntity.ok(fachada.listarEditaisPublicados(pageable));
    }

    @GetMapping("/{editalId}/etapas")
    @Operation(summary = "Listar Etapas de um Edital", description = "Lista todas as etapas de um edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista de etapas de um edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<EtapaResponse>> listarEtapasPorEdital(@PathVariable Long editalId) {
        List<EtapaResponse> response = fachada.listarEtapasPorEdital(editalId).stream()
                .map(etapa -> new EtapaResponse(etapa, modelMapper))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{editalId}/campos")
    @Operation(summary = "Listar Campos Personalizados de um Edital", description = "Lista todos os campos personalizados de um edital pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista de campos personalizados de um edital.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorEdital(@PathVariable Long editalId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorEdital(editalId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Edital por ID", description = "Deleta o edital pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Tipo de edital deletado.")
    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
    @ApiResponse(responseCode = "404", description = "Edital não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EditalNotFoundException {
        fachada.deletarEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}