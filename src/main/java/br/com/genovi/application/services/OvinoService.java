package br.com.genovi.application.services;

import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Criador;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.infrastructure.mappers.OvinoMapper;
import br.com.genovi.infrastructure.repositories.AscendenciaRepository;
import br.com.genovi.infrastructure.repositories.CriadorRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
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

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

    private Ascendencia findAscendenciaEntityById(Long id) {
        return ascendenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ascendência não encontrada"));
    }

    private Criador findCriadorEntityById(Long id) {
        return criadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));
    }

    public List<OvinoDTO> findAll() {
        return ovinoRepository.findAll().stream()
                .map(ovinoMapper::toDTO)
                .toList();
    }

    public OvinoDTO findById(Long id) {
        return ovinoMapper.toDTO(findOvinoEntityById(id));
    }

    public OvinoDTO save(CreateOvinoDTO dto) {
        Criador criador = findCriadorEntityById(dto.criadorId());
        Ascendencia ascendencia = findAscendenciaEntityById(dto.ascendenciaId());

        Ovino ovino = ovinoMapper.toEntity(dto, true, criador, ascendencia);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public OvinoDTO update(Long id, CreateOvinoDTO dto) {
        Ovino ovino = findOvinoEntityById(id);
        Criador criador = findCriadorEntityById(dto.criadorId());
        Ascendencia ascendencia = findAscendenciaEntityById(dto.ascendenciaId());

        ovinoMapper.updateEntityFromDTO(dto, true, ovino, criador, ascendencia);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public void disable(Long id) {
        Ovino ovino = findOvinoEntityById(id);
        ovino.setAtivo(false);
        ovinoRepository.save(ovino);
    }
}
