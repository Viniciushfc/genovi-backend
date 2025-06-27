package br.com.genovi.application.services;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import br.com.genovi.infrastructure.mappers.ReproducaoMapper;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReproducaoService {

    private final ReproducaoRepository reproducaoRepository;
    private final OvinoRepository ovinoRepository;
    private final ReproducaoMapper reproducaoMapper;

    public ReproducaoService(ReproducaoRepository reproducaoRepository, OvinoRepository ovinoRepository, ReproducaoMapper reproducaoMapper) {
        this.reproducaoRepository = reproducaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.reproducaoMapper = reproducaoMapper;
    }

    private Reproducao findReproducaoById(Long id) {
        return reproducaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Reproducao não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

    public List<ReproducaoDTO> findAll() {
        return reproducaoRepository.findAll().stream().map(reproducaoMapper::toDTO).toList();
    }

    public ReproducaoDTO findById(Long id) {
        return reproducaoMapper.toDTO(findReproducaoById(id));
    }

    public ReproducaoDTO save(CreateReproducaoDTO dto) {
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovelha = findOvinoEntityById(dto.ovelhaId());

        Reproducao reproducao = reproducaoMapper.toEntity(dto, carneiro, ovelha);

        reproducao = reproducaoRepository.save(reproducao);

        return reproducaoMapper.toDTO(reproducao);
    }

    public ReproducaoDTO update(Long id, CreateReproducaoDTO dto) {
        Reproducao reproducao = findReproducaoById(id);
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovelha = findOvinoEntityById(dto.ovelhaId());

        reproducaoMapper.updateEntetyFromDTO(dto, reproducao, carneiro, ovelha);

        reproducao = reproducaoRepository.save(reproducao);

        return reproducaoMapper.toDTO(reproducao);
    }

    public void delete(Long id) {
        findReproducaoById(id);
        reproducaoRepository.deleteById(id);
    }
}
