package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.OcorrenciaDoenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaDoencaRepository extends JpaRepository<OcorrenciaDoenca, Long> {
}
