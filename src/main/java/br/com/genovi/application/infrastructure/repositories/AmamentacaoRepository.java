package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Amamentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmamentacaoRepository extends JpaRepository<Amamentacao, Long> {

    List<Amamentacao> findByOvelhaMaeIdOrCordeiroMamandoId(Long idByOvelha, Long idByCordeiro);
}
