package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {
}
