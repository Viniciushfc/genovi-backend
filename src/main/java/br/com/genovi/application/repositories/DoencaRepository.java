package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {
}
