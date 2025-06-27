package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Ovino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvinoRepository extends JpaRepository<Ovino, Long> {

    List<Ovino> findByRfid(Long rfid);
}
