package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
