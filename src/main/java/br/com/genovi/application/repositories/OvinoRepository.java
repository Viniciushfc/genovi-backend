package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Ovino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OvinoRepository extends JpaRepository<Ovino, Long> {

    Optional<Ovino> findByRfid(Long rfid);
}
