package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaDoencaRepository extends JpaRepository<OcorrenciaDoenca, Long> {

    List<OcorrenciaDoenca> findByOvinoId(Long id);

    List<OcorrenciaDoenca> findByOvino(Ovino ovino);
}
