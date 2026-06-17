//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import java.util.UUID;
//
//public interface AuthServiceHandler {
//
//    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUsuarioPorId")
//    UsuarioResponse buscarUsuarioPorId(UUID userId);
//
//    @SuppressWarnings("unused")
//    UsuarioResponse fallbackBuscarUsuarioPorId(UUID userId, Throwable t);
//
//    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarCursoPorId")
//    CursoResponse buscarCursoPorId(Long id);
//
//    CursoResponse fallbackBuscarCursoPorId(Long id, Throwable t);
//}