package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Ascendencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AscendenciaRepository extends JpaRepository<Ascendencia, Long> {
}
