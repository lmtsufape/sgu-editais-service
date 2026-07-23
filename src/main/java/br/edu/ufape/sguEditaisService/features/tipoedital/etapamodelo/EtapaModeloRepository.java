/*package br.edu.ufape.sguEditaisService.features.tipoedital.etapamodelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EtapaModeloRepository extends JpaRepository<EtapaModelo, Long> {
    // Garante que as etapas venham sempre na ordem cronológica de execução do certame
    List<EtapaModelo> findByTipoEditalIdOrderByOrdemAsc(Long tipoEditalId);
}

 */