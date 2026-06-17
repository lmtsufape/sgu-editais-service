//package br.edu.ufape.sguEditaisService.dados;
//
//import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface HistoricoEtapaInscricaoRepository extends JpaRepository<HistoricoEtapaInscricao, Long> {
//    List<HistoricoEtapaInscricao> findByInscricaoIdOrderByDataMudancaDesc(Long inscricaoId);
//}

package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoEtapaInscricaoRepository extends JpaRepository<HistoricoEtapaInscricao, Long> {
    // Trilha de auditoria cronológica
    List<HistoricoEtapaInscricao> findByInscricaoIdOrderByDataMudancaDesc(Long inscricaoId);
}