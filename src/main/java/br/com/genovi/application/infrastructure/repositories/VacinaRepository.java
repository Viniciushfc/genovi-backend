package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
