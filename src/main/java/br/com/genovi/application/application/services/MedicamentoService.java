package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Doenca;
import br.com.genovi.application.domain.models.Medicamento;
import br.com.genovi.application.dtos.CreateMedicamentoDTO;
import br.com.genovi.application.dtos.MedicamentoDTO;
import br.com.genovi.application.infrastructure.mappers.MedicamentoMapper;
import br.com.genovi.application.infrastructure.repositories.DoencaRepository;
import br.com.genovi.application.infrastructure.repositories.MedicamentoRepository;
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
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com ID: " + id));
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
            throw new RuntimeException("Uma ou mais Doenças não foram encontradas.");
        }

        medicamentoMapper.updateEntityFromDTO(dto, medicamento, doencas);
        medicamentoRepository.save(medicamento);

        return medicamentoMapper.toDTO(medicamento);
    }

    public void delete(Long id) {
        Medicamento medicamento = findMedicamentoById(id);
        medicamentoRepository.delete(medicamento);
    }
}

