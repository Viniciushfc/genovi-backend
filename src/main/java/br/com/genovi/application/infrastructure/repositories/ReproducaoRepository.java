package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {

    List<Reproducao> findByCarneiroPaiIdOrOvelhaMaeId(Long idByCarneiro, Long idOvelhaMae);
}
