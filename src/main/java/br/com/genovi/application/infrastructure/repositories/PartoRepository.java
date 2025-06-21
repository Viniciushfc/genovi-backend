package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Parto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartoRepository extends JpaRepository<Parto, Long> {

    List<Parto> findByOvelhaMaeId(Long id);
}
