package br.edu.ufape.sguEditaisService.auth;

import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "authServiceClient", url = "${authClient.base-url}")
public interface AuthServiceClient {


    @GetMapping("/usuario/{userId}")
    UsuarioResponse buscarUsuarioPorId(@PathVariable("userId") UUID userId);


    @GetMapping("curso/{id}")
    CursoResponse buscarCursoPorId(@PathVariable("id") Long id);
}