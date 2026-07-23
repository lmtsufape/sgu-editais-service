/*package br.edu.ufape.sguEditaisService.features.tipoedital.campomodelo;

 import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
 import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
 import br.edu.ufape.sguEditaisService.features.tipoedital.CampoPersonalizado;
 import lombok.RequiredArgsConstructor;
 import org.springframework.stereotype.Service;
 import java.util.List;
 
 @Service
 @RequiredArgsConstructor
 public class CampoPersonalizadoServiceImpl implements CampoPersonalizadoService {
 
     private final CampoPersonalizadoRepository repository;
 
     @Override
     public CampoPersonalizado salvar(CampoPersonalizado campo) {
         return repository.save(campo);
     }
 
     @Override
     public CampoPersonalizado buscar(Long id) throws CampoPersonalizadoNotFoundException {
         return repository.findById(id).orElseThrow(() -> new TipoEditalNotFoundException(id));
     }
 
     @Override
     public List<CampoPersonalizado> listarPorTipoEdital(Long tipoEditalId) {
         return repository.findByTipoEditalId(tipoEditalId);
     }
     //Não há diferença entre template e edital
     @Override
     public List<CampoPersonalizado> listarPorTemplate(Long tipoEditalId) {
         return repository.findByTipoEditalId(tipoEditalId);
     }
 
     @Override
     public CampoPersonalizado editar(Long id, CampoPersonalizado campo) throws CampoPersonalizadoNotFoundException {
         CampoPersonalizado existente = buscar(id);
         existente.setTitulo(campo.getTitulo());
         existente.setTipoCampo(campo.getTipoCampo());
         existente.setObrigatorio(campo.isObrigatorio());
         return repository.save(existente);
     }
 
     @Override
     public void deletar(Long id) throws CampoPersonalizadoNotFoundException {
         repository.delete(buscar(id));
     }
 }


 */