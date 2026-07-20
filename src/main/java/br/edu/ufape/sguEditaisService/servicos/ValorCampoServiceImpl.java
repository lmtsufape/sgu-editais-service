package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.ValorCampoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import br.edu.ufape.sguEditaisService.servicos.interfaces.ValorCampoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValorCampoServiceImpl implements ValorCampoService {

    private final ValorCampoRepository repository;

    @Override
    public ValorCampo salvar(ValorCampo valorCampo) {
        return repository.save(valorCampo);
    }

    @Override
    public ValorCampo buscar(Long id) throws ValorCampoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ValorCampoNotFoundException(id));
    }

    @Override
    public List<ValorCampo> listarPorInscricao(Long inscricaoId) {
        return repository.findByInscricaoId(inscricaoId);
    }

    @Override
    public ValorCampo editar(Long id, ValorCampo valorCampo) throws ValorCampoNotFoundException {
        ValorCampo existente = buscar(id);
        existente.setValor(valorCampo.getValor()); // Atualiza o texto ou o path do novo arquivo
        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) throws ValorCampoNotFoundException {
        repository.delete(buscar(id));
    }
}