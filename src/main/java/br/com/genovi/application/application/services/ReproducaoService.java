package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Reproducao;
import br.com.genovi.application.dtos.CreateReproducaoDTO;
import br.com.genovi.application.dtos.ReproducaoDTO;
import br.com.genovi.application.infrastructure.mappers.ReproducaoMapper;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import br.com.genovi.application.infrastructure.repositories.ReproducaoRepository;
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
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        Reproducao reproducao = reproducaoMapper.toEntity(dto);
        reproducao.setCarneiroPai(carneiro);
        reproducao.setOvelhaMae(ovino);

        reproducao = reproducaoRepository.save(reproducao);

        return reproducaoMapper.toDTO(reproducao);
    }

    public ReproducaoDTO update(Long id, CreateReproducaoDTO dto) {
        Reproducao reproducao = findReproducaoById(id);
        Ovino carneiro = findOvinoEntityById(dto.carneiroId());
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        reproducaoMapper.updateEntetyFromDTO(dto, reproducao);
        reproducao.setCarneiroPai(carneiro);
        reproducao.setOvelhaMae(ovino);

        reproducao = reproducaoRepository.save(reproducao);

        return reproducaoMapper.toDTO(reproducao);
    }

    public void delete(Long id) {
        Reproducao reproducao = findReproducaoById(id);
        reproducaoRepository.deleteById(id);
    }
}
