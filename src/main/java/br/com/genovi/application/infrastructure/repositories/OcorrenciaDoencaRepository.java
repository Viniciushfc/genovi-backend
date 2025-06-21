package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.OcorrenciaDoenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaDoencaRepository extends JpaRepository<OcorrenciaDoenca, Long> {

    List<OcorrenciaDoenca> findByOvinoId(Long id);
}
