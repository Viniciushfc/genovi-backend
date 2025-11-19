package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.MedicamentoService;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.application.mapper.MedicamentoMapper;
import br.com.genovi.infrastructure.repository.DoencaRepository;
import br.com.genovi.infrastructure.repository.MedicamentoRepository;
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

        Medicamento entity = new Medicamento();
        entity.setNome(dto.nome());
        entity.setFabricante(dto.fabricante());
        entity.setDoencas(doencas);
        entity.setIntervaloDoses(dto.intervaloDoses());
        entity.setQuantidadeDoses(dto.quantidadeDoses());
        entity.setVacina(dto.isVacina());

        medicamentoRepository.save(entity);

        return medicamentoMapper.toDTO(entity);
    }

    @Override
    public MedicamentoDTO update(Long id, CreateMedicamentoDTO dto) {
        Medicamento entity = findMedicamentoById(id);

        List<Doenca> doencas = doencaRepository.findAllById(dto.doencasIds());

        if (doencas.size() != dto.doencasIds().size()) {
            throw new ResourceNotFoundException("Uma ou mais Doenças não foram encontradas.");
        }

        entity.setNome(dto.nome());
        entity.setFabricante(dto.fabricante());
        entity.setDoencas(doencas);
        entity.setIntervaloDoses(dto.intervaloDoses());
        entity.setQuantidadeDoses(dto.quantidadeDoses());
        entity.setVacina(dto.isVacina());

        medicamentoRepository.save(entity);

        return medicamentoMapper.toDTO(medicamentoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findMedicamentoById(id);
        medicamentoRepository.deleteById(id);
    }
}
