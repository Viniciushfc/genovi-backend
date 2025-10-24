package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Gestacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestacaoRepository extends JpaRepository<Gestacao, Long> {
}
