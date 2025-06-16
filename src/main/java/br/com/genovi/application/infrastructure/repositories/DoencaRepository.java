package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {
}
