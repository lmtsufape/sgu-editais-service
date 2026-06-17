//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.auth.AuthServiceClient;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
//import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.AuthServiceHandler;
//import feign.FeignException;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AuthServiceHandlerImpl implements AuthServiceHandler {
//
//    private final AuthServiceClient authServiceClient;
//
//    @Override
//    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUsuarioPorId")
//    public UsuarioResponse buscarUsuarioPorId(UUID userId) {
//        return authServiceClient.buscarUsuarioPorId(userId);
//    }
//
//    @Override
//    public UsuarioResponse fallbackBuscarUsuarioPorId(UUID userId, Throwable t) {
//        log.warn("AVISO: Falha ao buscar dados do USUÁRIO com ID {}. O serviço de autenticação pode estar indisponível. Erro: {}", userId, t.getMessage());
//        return new UsuarioResponse();
//    }
//
//    @Override
//    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarCursoPorId")
//    public CursoResponse buscarCursoPorId(Long id) {
//        return authServiceClient.buscarCursoPorId(id);
//    }
//
//    @Override
//    public CursoResponse fallbackBuscarCursoPorId(Long id, Throwable t) {
//        if (t instanceof FeignException.NotFound) {
//            log.warn("Curso com ID {} não encontrado no Auth Service.", id);
//            return null;
//        }
//        log.error("ERRO CRÍTICO: Falha ao validar Curso ID {}. O Auth Service pode estar indisponível. Erro: {}", id, t.getMessage());
//        return null;
//    }
//}