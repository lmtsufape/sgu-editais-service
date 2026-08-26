package br.edu.ufape.sguEditaisService.features.edital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditalRepository extends JpaRepository<Edital, Long> {
    List<Edital> findByModuloOrigem(String moduloOrigem);
}