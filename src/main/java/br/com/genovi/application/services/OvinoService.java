package br.com.genovi.application.services;

import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Criador;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.relatorios.GenealogiaDTO;
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

        Ovino pai = dto.paiId() != null ? findOvinoEntityById(dto.paiId()) : null;
        Ovino mae = dto.maeId() != null ? findOvinoEntityById(dto.maeId()) : null;

        Ascendencia ascendencia = new Ascendencia();
        ascendencia.setPai(pai);
        ascendencia.setMae(mae);
        ascendenciaRepository.save(ascendencia);

        Ovino ovino = ovinoMapper.toEntity(dto, dto.status(), criador, ascendencia);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public OvinoDTO update(Long id, CreateOvinoDTO dto) {
        Ovino ovino = findOvinoEntityById(id);
        Criador criador = findCriadorEntityById(dto.criadorId());

        Ovino pai = dto.paiId() != null ? findOvinoEntityById(dto.paiId()) : null;
        Ovino mae = dto.maeId() != null ? findOvinoEntityById(dto.maeId()) : null;

        Ascendencia ascendencia = ovino.getAscendencia();
        if (ascendencia == null) {
            ascendencia = new Ascendencia();
        }
        ascendencia.setPai(pai);
        ascendencia.setMae(mae);
        ascendenciaRepository.save(ascendencia);

        ovinoMapper.updateEntityFromDTO(dto, dto.status(), ovino, criador, ascendencia);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public void disable(Long id) {
        Ovino ovino = findOvinoEntityById(id);
        ovino.setStatus(TypeStatus.DESATIVADO);
        ovinoRepository.save(ovino);
    }

    public GenealogiaDTO familyTree(OvinoDTO ovino) {
        if (ovino == null || ovino.ascendencia() == null) {
            return new GenealogiaDTO(ovino, null, null);
        }

        OvinoDTO pai = ovino.ascendencia().ovinoPai();
        OvinoDTO mae = ovino.ascendencia().ovinoMae();

        // Recursão controlada
        GenealogiaDTO genealogiaPai = familyTree(pai);
        GenealogiaDTO genealogiaMae = familyTree(mae);

        return new GenealogiaDTO(ovino, genealogiaPai, genealogiaMae);
    }

}
