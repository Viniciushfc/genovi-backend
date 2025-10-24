package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
