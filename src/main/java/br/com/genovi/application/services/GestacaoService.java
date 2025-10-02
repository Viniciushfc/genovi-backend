package br.com.genovi.application.services;

import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.infrastructure.mappers.GestacaoMapper;
import br.com.genovi.infrastructure.repositories.GestacaoRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestacaoService {

    private final GestacaoRepository gestacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoRepository reproducaoRepository;
    private final GestacaoMapper gestacaoMapper;

    public GestacaoService(GestacaoRepository gestacaoRepository, OvinoRepository ovinoRepository, ReproducaoRepository reproducaoRepository, GestacaoMapper gestacaoMapper) {
        this.gestacaoRepository = gestacaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.reproducaoRepository = reproducaoRepository;
        this.gestacaoMapper = gestacaoMapper;
    }

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

    public List<GestacaoDTO> findAll() {
        return gestacaoRepository.findAll().stream().map(gestacaoMapper::toDTO).toList();
    }

    public GestacaoDTO findById(Long id) {
        return gestacaoMapper.toDTO(findGestacaoById(id));
    }

    public GestacaoDTO save(CreateGestacaoDTO dto) {
        Ovino ovinoMae = findOvinoById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoById(dto.ovelhaPaiId());
        Reproducao reproducao = findReproducaoById(dto.reproducaoId());

        Gestacao gestacao = gestacaoMapper.toEntity(dto, ovinoMae, ovinoPai, reproducao);

        return gestacaoMapper.toDTO(gestacaoRepository.save(gestacao));
    }

    public GestacaoDTO update(Long id, CreateGestacaoDTO dto) {
        Gestacao entity = findGestacaoById(id);
        Ovino ovinoMae = findOvinoById(dto.ovelhaMaeId());
        Ovino ovinoPai = findOvinoById(dto.ovelhaPaiId());
        Reproducao reproducao = findReproducaoById(dto.reproducaoId());

        entity = gestacaoMapper.toEntity(dto, ovinoMae, ovinoPai, reproducao);

        return gestacaoMapper.toDTO(gestacaoRepository.save(entity));
    }

    public void delete(Long id) {
        findGestacaoById(id);
        gestacaoRepository.deleteById(id);
    }
}