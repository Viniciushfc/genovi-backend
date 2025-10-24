package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.GestacaoService;
import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.application.mapper.GestacaoMapper;
import br.com.genovi.infrastructure.repositories.GestacaoRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GestacaoServiceImpl implements GestacaoService {

    private final GestacaoRepository gestacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoRepository reproducaoRepository;
    private final GestacaoMapper gestacaoMapper;

    private Gestacao findGestacaoById(Long id) {
        if (id == null) return null;
        return gestacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gestação não encontrada"));
    }

    private Ovino findOvinoById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrada"));
    }

    private Reproducao findReproducaoById(Long id) {
        if (id == null) return null;
        return reproducaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reprodução não encontrada"));
    }

    @Override
    public List<GestacaoDTO> findAll() {
        return gestacaoRepository.findAll().stream().map(gestacaoMapper::toDTO).toList();
    }

    @Override
    public GestacaoDTO findById(Long id) {
        return gestacaoMapper.toDTO(findGestacaoById(id));
    }

    @Override
    public GestacaoDTO save(CreateGestacaoDTO dto) {
        Ovino ovinoMae = findOvinoById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoById(dto.ovelhaPaiId());
        Reproducao reproducao = findReproducaoById(dto.reproducaoId());

        Gestacao gestacao = gestacaoMapper.toEntity(dto, ovinoMae, ovinoPai, reproducao);

        return gestacaoMapper.toDTO(gestacaoRepository.save(gestacao));
    }

    @Override
    public GestacaoDTO update(Long id, CreateGestacaoDTO dto) {
        Gestacao entity = findGestacaoById(id);
        Ovino ovinoMae = findOvinoById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoById(dto.ovelhaPaiId());
        Reproducao reproducao = findReproducaoById(dto.reproducaoId());

        entity.setOvelhaMae(ovinoMae);
        entity.setOvelhaPai(ovinoPai);
        entity.setReproducao(reproducao);
        entity.setDataGestacao(dto.dataGestacao());

        return gestacaoMapper.toDTO(gestacaoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findGestacaoById(id);
        gestacaoRepository.deleteById(id);
    }
}
