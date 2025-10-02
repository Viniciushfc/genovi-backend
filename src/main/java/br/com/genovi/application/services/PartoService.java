package br.com.genovi.application.services;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.infrastructure.mappers.PartoMapper;
import br.com.genovi.infrastructure.repositories.GestacaoRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.PartoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartoService {

    private final PartoRepository partoRepository;
    private final OvinoRepository ovinoRepository;
    private final GestacaoRepository gestacaoRepository;
    private final PartoMapper partoMapper;

    public PartoService(PartoRepository partoRepository, OvinoRepository ovinoRepository, GestacaoRepository gestacaoRepository, PartoMapper partoMapper) {
        this.partoRepository = partoRepository;
        this.ovinoRepository = ovinoRepository;
        this.gestacaoRepository = gestacaoRepository;
        this.partoMapper = partoMapper;
    }

    private Parto findPartoEntityById(Long id) {
        if (id == null) return null;
        return partoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parto não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para Parto"));
    }

    private Gestacao findGestacaoEntityById(Long id) {
        if (id == null) return null;
        return gestacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gestação não encontrada para Parto"));
    }

    public List<PartoDTO> findAll() {
        return partoRepository.findAll().stream().map(partoMapper::toDTO).toList();
    }

    public PartoDTO findById(Long id) {
        return partoMapper.toDTO(findPartoEntityById(id));
    }

    public PartoDTO save(CreatePartoDTO dto) {
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoEntityById(dto.ovelhaPaiId());
        Gestacao gestacao = findGestacaoEntityById(dto.gestacaoId());

        Parto parto = partoMapper.toEntity(dto, ovinoMae, ovinoPai, gestacao);

        partoRepository.save(parto);

        return partoMapper.toDTO(parto);
    }

    public PartoDTO update(Long id, CreatePartoDTO dto) {
        Parto entity = findPartoEntityById(id);
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoEntityById(dto.ovelhaPaiId());
        Gestacao gestacao = findGestacaoEntityById(dto.gestacaoId());

        Long existingId = entity.getId();
        Parto updatedParto = partoMapper.toEntity(dto, ovinoMae, ovinoPai, gestacao);
        updatedParto.setId(existingId);
        partoRepository.save(updatedParto);

        return partoMapper.toDTO(updatedParto);
    }

    public void delete(Long id) {
        findPartoEntityById(id);
        partoRepository.deleteById(id);
    }
}
