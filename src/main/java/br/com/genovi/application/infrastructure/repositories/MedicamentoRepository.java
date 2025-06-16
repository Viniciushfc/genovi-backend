package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
