package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Amamentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmamentacaoRepository extends JpaRepository<Amamentacao, Long> {
}
