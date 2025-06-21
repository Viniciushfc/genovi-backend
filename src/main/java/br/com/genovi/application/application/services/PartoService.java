package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Parto;
import br.com.genovi.application.domain.models.Reproducao;
import br.com.genovi.application.dtos.CreatePartoDTO;
import br.com.genovi.application.dtos.PartoDTO;
import br.com.genovi.application.infrastructure.mappers.PartoMapper;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import br.com.genovi.application.infrastructure.repositories.PartoRepository;
import br.com.genovi.application.infrastructure.repositories.ReproducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartoService {

    private final PartoRepository partoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoRepository reproducaoRepository;
    private final PartoMapper partoMapper;

    public PartoService(PartoRepository partoRepository, OvinoRepository ovinoRepository, ReproducaoRepository reproducaoRepository, PartoMapper partoMapper) {
        this.partoRepository = partoRepository;
        this.ovinoRepository = ovinoRepository;
        this.reproducaoRepository = reproducaoRepository;
        this.partoMapper = partoMapper;
    }

    private Parto findPartoEntityById(Long id) {
        return partoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parto não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado para Parto"));
    }

    private Reproducao findReproducaoEntityById(Long id) {
        return reproducaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reproducao não encontrado para Parto"));
    }

    public List<PartoDTO> findAll() {
        return partoRepository.findAll().stream().map(partoMapper::toDTO).toList();
    }

    public PartoDTO findById(Long id) {
        return partoMapper.toDTO(findPartoEntityById(id));
    }

    public PartoDTO save(CreatePartoDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovelhaMaeId());
        Reproducao reproducao = findReproducaoEntityById(dto.reproducaoOrigemId());

        List<Ovino> animaisCriados = ovinoRepository.findAllById(dto.animaisCriadosIds());

        if (animaisCriados.size() != dto.animaisCriadosIds().size()) {
            throw new RuntimeException("Um ou mais Animais Criados não foram encontrados.");
        }

        Parto parto = partoMapper.toEntity(dto, ovino, animaisCriados, reproducao);

        partoRepository.save(parto);

        return partoMapper.toDTO(parto);
    }

    public PartoDTO update(Long id, CreatePartoDTO dto) {
        Parto parto = findPartoEntityById(id);
        Ovino ovino = findOvinoEntityById(dto.ovelhaMaeId());
        Reproducao reproducao = findReproducaoEntityById(dto.reproducaoOrigemId());

        List<Ovino> animaisCriados = ovinoRepository.findAllById(dto.animaisCriadosIds());

        if (animaisCriados.size() != dto.animaisCriadosIds().size()) {
            throw new RuntimeException("Um ou mais Animais Criados não foram encontrados.");
        }

        partoMapper.updateEntity(dto, parto, ovino, animaisCriados, reproducao);

        return partoMapper.toDTO(parto);
    }

    public void delete(Long id) {
        findPartoEntityById(id);
        partoRepository.deleteById(id);
    }
}
