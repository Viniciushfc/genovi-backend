package br.com.genovi.application.services;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.infrastructure.mappers.MedicamentoMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
import br.com.genovi.infrastructure.repositories.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final DoencaRepository doencaRepository;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoService(
            MedicamentoRepository medicamentoRepository,
            DoencaRepository doencaRepository,
            MedicamentoMapper medicamentoMapper) {
        this.medicamentoRepository = medicamentoRepository;
        this.doencaRepository = doencaRepository;
        this.medicamentoMapper = medicamentoMapper;
    }

    private Medicamento findMedicamentoById(Long id) {
        if (id == null) return null;
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado com ID: " + id));
    }

    public List<MedicamentoDTO> findAll() {
        return medicamentoRepository.findAll().stream()
                .map(medicamentoMapper::toDTO)
                .toList();
    }

    public MedicamentoDTO findById(Long id) {
        return medicamentoMapper.toDTO(findMedicamentoById(id));
    }

    public MedicamentoDTO save(CreateMedicamentoDTO dto) {
        List<Doenca> doencas = doencaRepository.findAllById(dto.doencasIds());

        if (doencas.size() != dto.doencasIds().size()) {
            throw new RuntimeException("Uma ou mais Doenças não foram encontradas.");
        }

        Medicamento medicamento = medicamentoMapper.toEntity(dto, doencas);
        medicamentoRepository.save(medicamento);

        return medicamentoMapper.toDTO(medicamento);
    }

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

    public void delete(Long id) {
        findMedicamentoById(id);
        medicamentoRepository.deleteById(id);
    }
}

