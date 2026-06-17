////package br.edu.ufape.sguEditaisService.comunicacao.controllers;
////
////import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
////import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
////import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.StatusInscricaoPatchRequest;
////import br.edu.ufape.sguEditaisService.exceptions.ApiErrorResponse;
////import br.edu.ufape.sguEditaisService.exceptions.ApiValidationErrorResponse;
////import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
////import br.edu.ufape.sguEditaisService.fachada.Fachada;
////import br.edu.ufape.sguEditaisService.models.Inscricao;
////import io.swagger.v3.oas.annotations.Operation;
////import io.swagger.v3.oas.annotations.media.Content;
////import io.swagger.v3.oas.annotations.media.Schema;
////import io.swagger.v3.oas.annotations.responses.ApiResponse;
////import io.swagger.v3.oas.annotations.tags.Tag;
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////import org.modelmapper.ModelMapper;
////import org.springdoc.core.annotations.ParameterObject;
////import org.springframework.data.domain.*;
////import org.springframework.data.web.PageableDefault;
////import org.springframework.http.*;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@RestController
////@RequiredArgsConstructor
////@RequestMapping("/inscricao")
////@Tag(name = "Inscrição", description = "Responsável pelas inscrições")
////public class InscricaoController {
////    private final Fachada fachada;
////    private final ModelMapper modelMapper;
////
////    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
////    @PostMapping
////    @Operation(summary = "Cadastrar Inscrição", description = "Cadastra uma nova Inscrição.")
////    @ApiResponse(responseCode = "201", description = "Operação bem-sucedida. Retorna informações da Inscrição cadastrada.")
////    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
////    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "404", description = "Propriedades não encontradas.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    @ApiResponse(responseCode = "409", description = "Conflito.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    public ResponseEntity<InscricaoResponse> salvar(@Valid @RequestBody InscricaoRequest request) {
////        Inscricao entity = request.convertToEntity(request, modelMapper);
////        InscricaoResponse salvo = fachada.salvarInscricao(entity);
////        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
////    }
////
////    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
////    @PatchMapping("/{id}")
////    @Operation(summary = "Editar Inscrição por ID", description = "Edita a Inscrição pelo seu ID.")
////    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Inscrição editada.")
////    @ApiResponse(responseCode = "400", description = "Erro na requisição.", content = @Content(schema = @Schema(oneOf = {ApiValidationErrorResponse.class, ApiErrorResponse.class})))
////    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "404", description = "Inscrição não encontrado para edição.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    public ResponseEntity<InscricaoResponse> editar(@PathVariable Long id, @Valid @RequestBody InscricaoRequest request) throws InscricaoNotFoundException {
////        Inscricao entity = request.convertToEntity(request, modelMapper);
////        InscricaoResponse atualizado = fachada.editarInscricao(id, entity);
////        return new ResponseEntity<>(atualizado, HttpStatus.OK);
////    }
////
////    @GetMapping("/{id}")
////    @Operation(summary = "Busca Inscrição por ID", description = "Retorna a Inscrição pelo seu ID.")
////    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna a Inscrição.")
////    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    public ResponseEntity<InscricaoResponse> buscar(@PathVariable Long id) throws InscricaoNotFoundException {
////        InscricaoResponse entity = fachada.buscarPorIdInscricao(id);
////        return new ResponseEntity<>(entity, HttpStatus.OK);
////    }
////
////    @GetMapping
////    @Operation(summary = "Listar Inscrições", description = "Lista todas as Inscrições de forma paginada.")
////    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida. Retorna uma lista paginada das Inscrições.")
////    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    public ResponseEntity<Page<InscricaoResponse>> listar(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
////        Page<InscricaoResponse> response = fachada.listarInscricao(pageable); // Agora retorna Page<InscricaoResponse>
////        return ResponseEntity.ok(response);
////    }
////
////    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
////    @DeleteMapping("/{id}")
////    @Operation(summary = "Deletar Inscrição por ID", description = "Deleta a Inscrição pelo seu ID.")
////    @ApiResponse(responseCode = "204", description = "Operação bem-sucedida. Inscrição deletada.")
////    @ApiResponse(responseCode = "401", description = "Não autorizado. Falha na autenticação.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para acessar este recurso.", content = @Content(schema = @Schema(implementation = Void.class)))
////    @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
////    public ResponseEntity<Void> deletar(@PathVariable Long id) throws InscricaoNotFoundException {
////        fachada.deletarInscricao(id);
////        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////    }
////
////    @PatchMapping("/{id}/status")
////    @Operation(summary = "Atualizar Status (Avaliação)", description = "Altera o status da inscrição. Se o status escolhido tiver a flag 'concluiEtapa', o sistema avança a inscrição automaticamente.")
////    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso.")
////    @ApiResponse(responseCode = "404", description = "Inscrição ou Status não encontrado.")
////    public ResponseEntity<InscricaoResponse> atualizarStatus(
////            @PathVariable Long id,
////            @Valid @RequestBody StatusInscricaoPatchRequest request) {
////
////        InscricaoResponse response = fachada.atualizarStatusInscricao(id, request.getStatusId(), request.getObservacao());
////        return ResponseEntity.ok(response);
////    }
////
////
////    @GetMapping("/atual")
////    @Operation(summary = "Listar Minhas Inscrições", description = "Retorna todas as inscrições do usuário autenticado.")
////    @ApiResponse(responseCode = "200", description = "Lista de inscrições retornada com sucesso.")
////    @ApiResponse(responseCode = "401", description = "Não autorizado.")
////    public ResponseEntity<List<InscricaoResponse>> listarInscricoesUsuarioLogado() {
////        List<InscricaoResponse> response = fachada.listarInscricoesUsuarioLogado();
////        return ResponseEntity.ok(response);
////    }
////}
//
//package br.edu.ufape.sguEditaisService.comunicacao.controllers;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.AvaliacaoInscricaoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
//import br.edu.ufape.sguEditaisService.fachada.Fachada;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/inscricao")
//@RequiredArgsConstructor
//public class InscricaoController {
//
//    private final Fachada fachada;
//    private final ObjectMapper objectMapper;
//
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<InscricaoResponse> submeterInscricao(
//            @Valid @RequestPart(value = "inscricao", required = false) InscricaoRequest request,
//            @RequestPart(value = "arquivos", required = false) MultipartFile[] arquivos,
//            @AuthenticationPrincipal Jwt jwt
//    ) throws IOException {
//
//        UUID userIdLogado = UUID.fromString(jwt.getSubject());
//
//        InscricaoResponse response = fachada.processarInscricao(request, arquivos, userIdLogado);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<InscricaoResponse> buscarPorId(@PathVariable Long id) {
//        return ResponseEntity.ok(fachada.buscarInscricaoPorId(id));
//    }
//
//    @GetMapping("/protocolo/{protocolo}")
//    public ResponseEntity<InscricaoResponse> buscarPorProtocolo(@PathVariable String protocolo) {
//        return ResponseEntity.ok(fachada.buscarInscricaoPorProtocolo(protocolo));
//    }
//
//    @GetMapping("/usuario/{userId}")
//    public ResponseEntity<List<InscricaoResponse>> listarPorUsuario(@PathVariable UUID userId) {
//        return ResponseEntity.ok(fachada.listarInscricoesPorUsuario(userId));
//    }
//
//    @GetMapping("/edital/{editalId}")
//    public ResponseEntity<List<InscricaoResponse>> listarPorEdital(@PathVariable Long editalId) {
//        return ResponseEntity.ok(fachada.listarInscricoesPorEdital(editalId));
//    }
//
//    @PatchMapping("/{id}/avaliar")
//    public ResponseEntity<InscricaoResponse> avaliarInscricao(
//            @PathVariable Long id,
//            @Valid @RequestBody AvaliacaoInscricaoRequest request,
//            @AuthenticationPrincipal Jwt jwt) {
//
//        // Extrai o UUID do usuário que fez a requisição
//        UUID avaliadorId = UUID.fromString(jwt.getSubject());
//
//        // Passa o ID seguro para a Fachada
//        return ResponseEntity.ok(fachada.avaliarInscricao(id, request, avaliadorId));
//    }
//}
