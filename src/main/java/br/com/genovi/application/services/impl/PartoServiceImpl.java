package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.PartoService;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.application.mapper.PartoMapper;
import br.com.genovi.infrastructure.repository.GestacaoRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.PartoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PartoServiceImpl implements PartoService {

    private final PartoRepository partoRepository;
    private final OvinoRepository ovinoRepository;
    private final GestacaoRepository gestacaoRepository;
    private final PartoMapper partoMapper;

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

    @Override
    public List<PartoDTO> findAll() {
        return partoRepository.findAll().stream().map(partoMapper::toDTO).toList();
    }

    @Override
    public PartoDTO findById(Long id) {
        return partoMapper.toDTO(findPartoEntityById(id));
    }

    @Override
    public PartoDTO save(CreatePartoDTO dto) {
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoEntityById(dto.ovelhaPaiId());
        Gestacao gestacao = findGestacaoEntityById(dto.gestacaoId());

        Parto parto = partoMapper.toEntity(dto, ovinoMae, ovinoPai, gestacao);

        partoRepository.save(parto);

        return partoMapper.toDTO(parto);
    }

    @Override
    public PartoDTO update(Long id, CreatePartoDTO dto) {
        Parto entity = findPartoEntityById(id);
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoEntityById(dto.ovelhaPaiId());
        Gestacao gestacao = findGestacaoEntityById(dto.gestacaoId());

        entity.setOvinoMae(ovinoMae);
        entity.setOvinoPai(ovinoPai);
        entity.setGestacao(gestacao);
        entity.setDataParto(dto.dataParto());

        return partoMapper.toDTO(partoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findPartoEntityById(id);
        partoRepository.deleteById(id);
    }
}
