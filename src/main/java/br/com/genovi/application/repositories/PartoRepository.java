package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Parto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartoRepository extends JpaRepository<Parto, Long> {
}
