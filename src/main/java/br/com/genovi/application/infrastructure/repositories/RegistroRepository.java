package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
