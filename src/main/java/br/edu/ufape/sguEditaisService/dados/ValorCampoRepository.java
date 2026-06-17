//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.ValorCampo;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface ValorCampoRepository extends JpaRepository<ValorCampo, Long> {
//    List<ValorCampo> findByInscricaoId(Long inscricaoId);
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.ValorCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorCampoRepository extends JpaRepository<ValorCampo, Long> {
    List<ValorCampo> findByInscricaoId(Long inscricaoId);
}