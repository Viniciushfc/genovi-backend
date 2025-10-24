package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {
}
