//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.TipoEdital;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface TipoEditalRepository extends JpaRepository<TipoEdital, Long> {
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.TipoEdital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoEditalRepository extends JpaRepository<TipoEdital, Long> {
    // Útil para garantir isolamento de domínio no futuro: "Listar templates do módulo X"
    List<TipoEdital> findByModuloOrigem(String moduloOrigem);

    boolean existsByNome(String nome);
}