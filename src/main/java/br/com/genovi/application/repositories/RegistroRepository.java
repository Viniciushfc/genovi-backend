package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
