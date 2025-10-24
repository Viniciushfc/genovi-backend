package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.AscendenciaService;
import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.AscendenciaMapper;
import br.com.genovi.infrastructure.repositories.AscendenciaRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AscendenciaServiceImpl implements AscendenciaService {

    private final AscendenciaRepository ascendenciaRepository;
    private final OvinoRepository ovinoRepository;
    private final AscendenciaMapper ascendenciaMapper;

    private Ascendencia findAscendenciaEntityById(Long id) {
        if (id == null) return null;
        return ascendenciaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ascendencia não encontrada"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) {
            return null;
        }

        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para Ascendencia"));
    }

    @Override
    public List<AscendenciaDTO> findAll() {
        return ascendenciaRepository.findAll().stream().map(ascendenciaMapper::toDTO).toList();
    }

    @Override
    public AscendenciaDTO findById(Long id) {
        return ascendenciaMapper.toDTO(findAscendenciaEntityById(id));
    }

    @Override
    public AscendenciaDTO save(CreateAscendenciaDTO dto) {
        Ovino ovinoPai = findOvinoEntityById(dto.idOvinoPai());
        Ovino ovinoMae = findOvinoEntityById(dto.idOvinoMae());

        Ascendencia ascendencia = ascendenciaMapper.toEntity(dto, ovinoPai, ovinoMae);

        ascendenciaRepository.save(ascendencia);
        return ascendenciaMapper.toDTO(ascendencia);
    }

    @Override
    public AscendenciaDTO update(Long id, CreateAscendenciaDTO dto) {
        Ascendencia entity = findAscendenciaEntityById(id);
        Ovino ovinoPai = findOvinoEntityById(dto.idOvinoPai());
        Ovino ovinoMae = findOvinoEntityById(dto.idOvinoMae());

        entity.setPai(ovinoPai);
        entity.setMae(ovinoMae);

        return ascendenciaMapper.toDTO(ascendenciaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findAscendenciaEntityById(id);
        ascendenciaRepository.deleteById(id);
    }
}
