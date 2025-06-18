package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Amamentacao;
import br.com.genovi.application.domain.models.Ascendencia;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.AscendenciaDTO;
import br.com.genovi.application.dtos.CreateAscendenciaDTO;
import br.com.genovi.application.infrastructure.mappers.AscendenciaMapper;
import br.com.genovi.application.infrastructure.repositories.AscendenciaRepository;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AscendenciaService {

    private final AscendenciaRepository ascendenciaRepository;
    private final OvinoRepository ovinoRepository;
    private final AscendenciaMapper ascendenciaMapper;

    public AscendenciaService(AscendenciaRepository ascendenciaRepository, OvinoRepository ovinoRepository, AscendenciaMapper ascendenciaMapper) {
        this.ascendenciaRepository = ascendenciaRepository;
        this.ovinoRepository = ovinoRepository;
        this.ascendenciaMapper = ascendenciaMapper;
    }

    public Ascendencia findAscendenciaEntityById(Long id) {
        return ascendenciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ascendencia não encontrada"));
    }

    public List<AscendenciaDTO> findAll() {
        return ascendenciaRepository.findAll().stream().map(ascendenciaMapper::toDTO).toList();
    }

    public AscendenciaDTO findById(Long id) {
        Ascendencia ascedencia = ascendenciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ascendencia não encontrada"));
        return ascendenciaMapper.toDTO(ascedencia);
    }

    public AscendenciaDTO save(CreateAscendenciaDTO dto) {
        Ovino ovinoPai = ovinoRepository.findById(dto.idOvinoPai()).orElseThrow(() -> new RuntimeException("Ovino Pai não encontrado"));
        Ovino ovinoMae = ovinoRepository.findById(dto.idOvinoMae()).orElseThrow(() -> new RuntimeException("Ovino Mae não encontrado"));

        Ascendencia ascendencia = ascendenciaMapper.toEntity(dto);
        ascendencia.setPai(ovinoPai);
        ascendencia.setMae(ovinoMae);

        ascendenciaRepository.save(ascendencia);
        return ascendenciaMapper.toDTO(ascendencia);
    }

    public AscendenciaDTO update(Long id, CreateAscendenciaDTO dto) {
        Ascendencia ascendencia = findAscendenciaEntityById(id);
        Ovino ovinoPai = ovinoRepository.findById(dto.idOvinoPai()).orElseThrow(() -> new RuntimeException("Ovino Pai não encontrado"));
        Ovino ovinoMae = ovinoRepository.findById(dto.idOvinoMae()).orElseThrow(() -> new RuntimeException("Ovino Mae não encontrado"));

        ascendencia.setPai(ovinoPai);
        ascendencia.setMae(ovinoMae);

        ascendenciaRepository.save(ascendencia);
        return ascendenciaMapper.toDTO(ascendencia);
    }

    public void delete(Long id) {
        Ascendencia ascendencia = findAscendenciaEntityById(id);
        ascendenciaRepository.deleteById(id);
    }
}
