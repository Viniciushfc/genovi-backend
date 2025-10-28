package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.ReproducaoService;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import br.com.genovi.application.mapper.ReproducaoMapper;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.ReproducaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReproducaoServiceImpl implements ReproducaoService {

    private final ReproducaoRepository reproducaoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoMapper reproducaoMapper;

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
        Reproducao entity = findReproducaoById(id);
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovelha = findOvinoEntityById(dto.ovelhaId());

        entity.setCarneiroPai(carneiro);
        entity.setOvelhaMae(ovelha);
        entity.setDataReproducao(dto.dataReproducao());
        entity.setEnumReproducao(dto.enumReproducao());
        entity.setObservacoes(dto.observacoes());

        return reproducaoMapper.toDTO(reproducaoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findReproducaoById(id);
        reproducaoRepository.deleteById(id);
    }
}
