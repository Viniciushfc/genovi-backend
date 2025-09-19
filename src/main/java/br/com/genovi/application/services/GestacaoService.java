package br.com.genovi.application.services;

import br.com.genovi.domain.models.Gestacao;
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
        return gestacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gestação não encontrada"));
    }

    private Ovino findOvinoById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrada"));
    }

    private Reproducao findReproducaoById(Long id) {
        return reproducaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reprodução não encontrada"));
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

        gestacaoMapper.updateEntityFromDTO(dto, entity, ovinoMae, ovinoPai, reproducao);

        return gestacaoMapper.toDTO(gestacaoRepository.save(entity));
    }

    public void delete(Long id) {
        findGestacaoById(id);
        gestacaoRepository.deleteById(id);
    }
}