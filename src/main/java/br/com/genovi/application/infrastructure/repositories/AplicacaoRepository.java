package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Aplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

}
