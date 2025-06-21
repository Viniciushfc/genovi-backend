package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Ovino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OvinoRepository extends JpaRepository<Ovino, Long> {

    List<Ovino> findByRfid(Long rfid);
}
