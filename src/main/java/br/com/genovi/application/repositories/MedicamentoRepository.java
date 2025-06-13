package br.com.genovi.application.repositories;

import br.com.genovi.application.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
