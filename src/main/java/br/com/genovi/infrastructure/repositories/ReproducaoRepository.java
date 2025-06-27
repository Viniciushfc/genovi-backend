package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {

    List<Reproducao> findByCarneiroPaiIdOrOvelhaMaeId(Long idByCarneiro, Long idOvelhaMae);
}
