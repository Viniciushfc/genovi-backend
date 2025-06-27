package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.CicloCio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CicloCioRepository extends JpaRepository<CicloCio, Long> {

    List<CicloCio> findByOvelhaId(Long id);
}