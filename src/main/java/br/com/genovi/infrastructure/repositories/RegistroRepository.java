package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
