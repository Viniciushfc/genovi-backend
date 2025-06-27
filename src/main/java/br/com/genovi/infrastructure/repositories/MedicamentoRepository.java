package br.com.genovi.infrastructure.repositories;

import br.com.genovi.domain.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
