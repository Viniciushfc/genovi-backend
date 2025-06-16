package br.com.genovi.application.services;

import br.com.genovi.application.domain.models.Ascendencia;
import br.com.genovi.application.domain.models.Criador;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.CreateOvinoDTO;
import br.com.genovi.application.dtos.OvinoDTO;
import br.com.genovi.application.infrastructure.mappers.OvinoMapper;
import br.com.genovi.application.infrastructure.repositories.AscendenciaRepository;
import br.com.genovi.application.infrastructure.repositories.CriadorRepository;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OvinoService {

    private final OvinoRepository ovinoRepository;
    private final AscendenciaRepository ascendenciaRepository;
    private final CriadorRepository criadorRepository;
    private final OvinoMapper ovinoMapper;

    public OvinoService(OvinoRepository ovinoRepository, AscendenciaRepository ascendenciaRepository, CriadorRepository criadorRepository, OvinoMapper ovinoMapper) {
        this.ovinoRepository = ovinoRepository;
        this.ascendenciaRepository = ascendenciaRepository;
        this.criadorRepository = criadorRepository;
        this.ovinoMapper = ovinoMapper;
    }

    public OvinoMapper getOvinoMapper() {
        return ovinoMapper;
    }

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

    public List<Ovino> findAll() {
        return ovinoRepository.findAll();
    }

    public OvinoDTO findById(Long id) {
        Ovino ovino = ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
        return ovinoMapper.toDTO(ovino);
    }

    public OvinoDTO save(CreateOvinoDTO dto) {
        Ascendencia ascendencia = ascendenciaRepository.findById(dto.ascendenciaId())
                .orElseThrow(() -> new RuntimeException("Ascendencia não encontrado"));

        Criador criador = criadorRepository.findById(dto.criadorId())
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));

        Ovino ovino = ovinoMapper.toEntity(dto);
        ovino.setAtivo(true);
        ovino.setAscendencia(ascendencia);
        ovino.setCriador(criador);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public OvinoDTO update(Long id, CreateOvinoDTO dto) {
        Ovino ovino = findOvinoEntityById(id);

        Ascendencia ascendencia = ascendenciaRepository.findById(dto.ascendenciaId())
                .orElseThrow(() -> new RuntimeException("Ascendência não encontrada"));

        Criador criador = criadorRepository.findById(dto.criadorId())
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));

        ovinoMapper.updateEntityFromDTO(dto, ovino);
        ovino.setAscendencia(ascendencia);
        ovino.setCriador(criador);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public void disable(Long id) {
        Ovino ovino = findOvinoEntityById(id);
        ovino.setAtivo(false);
    }
}
