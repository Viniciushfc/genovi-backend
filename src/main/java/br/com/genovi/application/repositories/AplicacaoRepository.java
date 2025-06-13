package br.com.genovi.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicacaoRepository extends JpaRepository<AplicacaoRepository, Long> {

}
