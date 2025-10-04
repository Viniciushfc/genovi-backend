package br.com.genovi.application.services;

import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;

import java.util.List;

public interface MedicamentoService {
    List<MedicamentoDTO> findAll();
    MedicamentoDTO findById(Long id);
    MedicamentoDTO save(CreateMedicamentoDTO dto);
    MedicamentoDTO update(Long id, CreateMedicamentoDTO dto);
    void delete(Long id);
}