package br.com.genovi.application.repositories;

import br.com.genovi.application.models.CicloCio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CicloCioRepository extends JpaRepository<CicloCio, Long> {
}
