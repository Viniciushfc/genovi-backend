package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Criador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriadorRepository extends JpaRepository<Criador, Long> {
}
