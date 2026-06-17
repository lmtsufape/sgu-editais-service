//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.Edital;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface EditalRepository extends JpaRepository<Edital, Long> {
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Edital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditalRepository extends JpaRepository<Edital, Long> {
    List<Edital> findByModuloOrigem(String moduloOrigem);
    List<Edital> findByCursoId(Long cursoId);
}