package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.ReproducaoService;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import br.com.genovi.infrastructure.mappers.ReproducaoMapper;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReproducaoServiceImpl implements ReproducaoService {

    private final ReproducaoRepository reproducaoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoMapper reproducaoMapper;

    public ReproducaoServiceImpl(ReproducaoRepository reproducaoRepository, OvinoRepository ovinoRepository, ReproducaoMapper reproducaoMapper) {
        this.reproducaoRepository = reproducaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.reproducaoMapper = reproducaoMapper;
    }

    private Reproducao findReproducaoById(Long id) {
        if (id == null) return null;
        return reproducaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reproducao não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado"));
    }

    @Override
    public List<ReproducaoDTO> findAll() {
        return reproducaoRepository.findAll().stream().map(reproducaoMapper::toDTO).toList();
    }

    @Override
    public ReproducaoDTO findById(Long id) {
        return reproducaoMapper.toDTO(findReproducaoById(id));
    }

    @Override
    public ReproducaoDTO save(CreateReproducaoDTO dto) {
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovelha = findOvinoEntityById(dto.ovelhaId());

        Reproducao reproducao = reproducaoMapper.toEntity(dto, carneiro, ovelha);

        reproducao = reproducaoRepository.save(reproducao);

        return reproducaoMapper.toDTO(reproducao);
    }

    @Override
    public ReproducaoDTO update(Long id, CreateReproducaoDTO dto) {
        Reproducao reproducao = findReproducaoById(id);
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovelha = findOvinoEntityById(dto.ovelhaId());

        Long existingId = reproducao.getId();
        Reproducao updatedReproducao = reproducaoMapper.toEntity(dto, carneiro, ovelha);
        updatedReproducao.setId(existingId);
        reproducaoRepository.save(updatedReproducao);

        return reproducaoMapper.toDTO(updatedReproducao);
    }

    @Override
    public void delete(Long id) {
        findReproducaoById(id);
        reproducaoRepository.deleteById(id);
    }
}
