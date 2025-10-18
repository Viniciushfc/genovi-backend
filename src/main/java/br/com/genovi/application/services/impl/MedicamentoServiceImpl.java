package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.MedicamentoService;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.infrastructure.mapper.MedicamentoMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
import br.com.genovi.infrastructure.repositories.MedicamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final DoencaRepository doencaRepository;
    private final MedicamentoMapper medicamentoMapper;

    private Medicamento findMedicamentoById(Long id) {
        if (id == null) return null;
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado com ID: " + id));
    }

    @Override
    public List<MedicamentoDTO> findAll() {
        return medicamentoRepository.findAll().stream()
                .map(medicamentoMapper::toDTO)
                .toList();
    }

    @Override
    public MedicamentoDTO findById(Long id) {
        return medicamentoMapper.toDTO(findMedicamentoById(id));
    }

    @Override
    public MedicamentoDTO save(CreateMedicamentoDTO dto) {
        List<Doenca> doencas = doencaRepository.findAllById(dto.doencasIds());

        if (doencas.size() != dto.doencasIds().size()) {
            throw new RuntimeException("Uma ou mais Doenças não foram encontradas.");
        }

        Medicamento medicamento = medicamentoMapper.toEntity(dto, doencas);
        medicamentoRepository.save(medicamento);

        return medicamentoMapper.toDTO(medicamento);
    }

    @Override
    public MedicamentoDTO update(Long id, CreateMedicamentoDTO dto) {
        Medicamento medicamento = findMedicamentoById(id);

        List<Doenca> doencas = doencaRepository.findAllById(dto.doencasIds());

        if (doencas.size() != dto.doencasIds().size()) {
            throw new ResourceNotFoundException("Uma ou mais Doenças não foram encontradas.");
        }

        Long existingId = medicamento.getId();
        Medicamento updatedMedicamento = medicamentoMapper.toEntity(dto, doencas);
        updatedMedicamento.setId(existingId);
        medicamentoRepository.save(updatedMedicamento);

        return medicamentoMapper.toDTO(updatedMedicamento);
    }

    @Override
    public void delete(Long id) {
        findMedicamentoById(id);
        medicamentoRepository.deleteById(id);
    }
}
