package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Amamentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmamentacaoRepository extends JpaRepository<Amamentacao, Long> {

    List<Amamentacao> findByOvelhaMaeIdOrCordeiroMamandoId(Long idByOvelha, Long idByCordeiro);
}
