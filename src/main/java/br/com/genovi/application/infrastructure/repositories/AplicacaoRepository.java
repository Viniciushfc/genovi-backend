package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Aplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByOvinoId(Long id);
}
