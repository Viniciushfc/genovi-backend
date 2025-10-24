package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Ascendencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AscendenciaRepository extends JpaRepository<Ascendencia, Long> {
}
