package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Aplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByOvinoId(Long id);
}
