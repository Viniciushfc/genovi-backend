package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Ovino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OvinoRepository extends JpaRepository<Ovino, Long> {

    Ovino findByRfid(@Param("rfid") Long rfid);
}
