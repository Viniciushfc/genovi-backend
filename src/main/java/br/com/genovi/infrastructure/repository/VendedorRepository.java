package br.com.genovi.infrastructure.repository;

import br.com.genovi.domain.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
