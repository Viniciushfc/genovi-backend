package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Criador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriadorRepository extends JpaRepository<Criador, Long> {
}
