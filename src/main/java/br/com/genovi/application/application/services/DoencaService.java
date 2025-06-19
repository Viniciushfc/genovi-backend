package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Doenca;
import br.com.genovi.application.dtos.DoencaDTO;
import br.com.genovi.application.infrastructure.mappers.DoencaMapper;
import br.com.genovi.application.infrastructure.repositories.DoencaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoencaService {

    private final DoencaRepository doencaRepository;
    private final DoencaMapper doencaMapper;

    public DoencaService(DoencaRepository doencaRepository, DoencaMapper doencaMapper) {
        this.doencaRepository = doencaRepository;
        this.doencaMapper = doencaMapper;
    }

    private Doenca findDoencaEntityById(Long id) {
        return doencaRepository.findById(id).orElseThrow(() -> new RuntimeException("Doença não encontrada"));
    }

    public List<DoencaDTO> findAll() {
        return doencaRepository.findAll().stream().map(doencaMapper::toDTO).toList();
    }

    public DoencaDTO findById(Long id) {
        return doencaMapper.toDTO(findDoencaEntityById(id));
    }

    public DoencaDTO save(DoencaDTO dto) {
        Doenca doenca = doencaMapper.toEntity(dto);
        return doencaMapper.toDTO(doencaRepository.save(doenca));
    }

    public DoencaDTO update(Long id, DoencaDTO dto) {
        Doenca doenca = findDoencaEntityById(id);
        doencaMapper.updateEntityFromDTO(dto, doenca);
        return doencaMapper.toDTO(doencaRepository.save(doenca));
    }

    public void delete(Long id) {
        findDoencaEntityById(id);
        doencaRepository.deleteById(id);
    }
}
