package br.com.genovi.application.repositories;

import br.com.genovi.application.models.OcorrenciaDoenca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaDoencaRepository extends JpaRepository<OcorrenciaDoenca, Long> {
}
