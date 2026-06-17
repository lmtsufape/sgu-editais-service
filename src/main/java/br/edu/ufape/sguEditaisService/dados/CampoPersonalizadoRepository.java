//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface CampoPersonalizadoRepository extends JpaRepository<CampoPersonalizado, Long> {
//    List<CampoPersonalizado> findByTipoEditalId(Long tipoEditalId);
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampoPersonalizadoRepository extends JpaRepository<CampoPersonalizado, Long> {
    List<CampoPersonalizado> findByTipoEditalId(Long tipoEditalId);
    List<CampoPersonalizado> findByEditalId(Long editalId);
}