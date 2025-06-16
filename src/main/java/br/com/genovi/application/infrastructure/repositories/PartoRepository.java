package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Parto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartoRepository extends JpaRepository<Parto, Long> {
}
