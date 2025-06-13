package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Criador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriadorRepository extends JpaRepository<Criador, Long> {
}
