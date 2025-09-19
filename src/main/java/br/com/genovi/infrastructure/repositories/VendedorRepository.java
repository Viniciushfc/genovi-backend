package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
