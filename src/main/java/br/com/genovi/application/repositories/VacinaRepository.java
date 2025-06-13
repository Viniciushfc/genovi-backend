package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
