package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {
}
