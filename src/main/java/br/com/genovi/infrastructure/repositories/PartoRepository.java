package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Parto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartoRepository extends JpaRepository<Parto, Long> {

    List<Parto> findByOvelhaMaeId(Long id);
}
