package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.OcorrenciaDoenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcorrenciaDoencaRepository extends JpaRepository<OcorrenciaDoenca, Long> {

    List<OcorrenciaDoenca> findByOvinoId(Long id);
}
