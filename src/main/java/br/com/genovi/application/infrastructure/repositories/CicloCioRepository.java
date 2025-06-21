package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.CicloCio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CicloCioRepository extends JpaRepository<CicloCio, Long> {

    List<CicloCio> findByOvelhaId(Long id);
}