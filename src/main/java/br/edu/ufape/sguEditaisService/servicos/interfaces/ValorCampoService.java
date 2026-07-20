package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.ValorCampo;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;
import java.util.List;

public interface ValorCampoService {
    ValorCampo salvar(ValorCampo valorCampo);
    ValorCampo buscar(Long id) throws ValorCampoNotFoundException;
    List<ValorCampo> listarPorInscricao(Long inscricaoId);
    ValorCampo editar(Long id, ValorCampo valorCampo) throws ValorCampoNotFoundException;
    void deletar(Long id) throws ValorCampoNotFoundException;
}