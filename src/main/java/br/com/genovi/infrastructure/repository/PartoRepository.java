package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Parto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartoRepository extends JpaRepository<Parto, Long> {

}
