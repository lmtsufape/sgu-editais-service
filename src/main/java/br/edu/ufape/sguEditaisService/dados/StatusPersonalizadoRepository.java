//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface StatusPersonalizadoRepository extends JpaRepository<StatusPersonalizado, Long> {
//    List<StatusPersonalizado> findByTipoStatus(TipoStatus tipoStatus);
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusPersonalizadoRepository extends JpaRepository<StatusPersonalizado, Long> {
    // Essencial para o frontend montar os Selects baseados no contexto (Inscrição vs Edital)
    List<StatusPersonalizado> findByTipoStatus(TipoStatus tipoStatus);
}