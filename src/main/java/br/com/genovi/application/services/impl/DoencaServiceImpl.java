package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.DoencaService;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.DoencaMapper;
import br.com.genovi.infrastructure.repository.DoencaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DoencaServiceImpl implements DoencaService {

    private final DoencaRepository doencaRepository;
    private final DoencaMapper doencaMapper;

    private Doenca findDoencaEntityById(Long id) {
        if (id == null) return null;
        return doencaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doença não encontrada"));
    }

    @Override
    public List<DoencaDTO> findAll() {
        return doencaRepository.findAll().stream().map(doencaMapper::toDTO).toList();
    }

    @Override
    public DoencaDTO findById(Long id) {
        return doencaMapper.toDTO(findDoencaEntityById(id));
    }

    @Override
    public DoencaDTO save(CreateDoencaDTO dto) {
        Doenca doenca = doencaMapper.toEntity(dto);
        return doencaMapper.toDTO(doencaRepository.save(doenca));
    }

    @Override
    public DoencaDTO update(Long id, CreateDoencaDTO dto) {
        Doenca entity = findDoencaEntityById(id);

        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());

        return doencaMapper.toDTO(doencaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findDoencaEntityById(id);
        doencaRepository.deleteById(id);
    }
}
