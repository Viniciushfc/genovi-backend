package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {

}
