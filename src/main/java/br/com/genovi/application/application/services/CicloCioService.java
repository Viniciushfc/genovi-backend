package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.CicloCio;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.CicloCioDTO;
import br.com.genovi.application.dtos.CreateCicloCioDTO;
import br.com.genovi.application.infrastructure.mappers.CicloCioMapper;
import br.com.genovi.application.infrastructure.repositories.CicloCioRepository;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CicloCioService {

    private final CicloCioRepository cicloCioRepository;
    private final OvinoRepository ovinoRepository;
    private final CicloCioMapper cicloCioMapper;

    public CicloCioService(CicloCioRepository cicloCioRepository, OvinoRepository ovinoRepository, CicloCioMapper cicloCioMapper) {
        this.cicloCioRepository = cicloCioRepository;
        this.ovinoRepository = ovinoRepository;
        this.cicloCioMapper = cicloCioMapper;
    }

    private CicloCio findCicloCioById(Long id) {
        return cicloCioRepository.findById(id).orElseThrow(() -> new RuntimeException("Ciclo e cio não encontrado"));
    }

    public List<CicloCioDTO> findAll() {
        return cicloCioRepository.findAll().stream().map(cicloCioMapper::toDTO).toList();
    }

    public CicloCioDTO findById(Long id) {
        return cicloCioMapper.toDTO(findCicloCioById(id));
    }

    public CicloCioDTO save(CreateCicloCioDTO dto) {
        Ovino ovino = ovinoRepository.findById(dto.ovelhaId()).orElseThrow(() -> new RuntimeException("Ovelha não encontrada para ciclo e cio"));

        CicloCio cicloCio = cicloCioMapper.toEntity(dto);
        cicloCio.setOvelha(ovino);

        cicloCioRepository.save(cicloCio);
        return cicloCioMapper.toDTO(cicloCio);
    }

    public CicloCioDTO update(Long id, CreateCicloCioDTO dto) {
        CicloCio cicloCio = findCicloCioById(id);
        Ovino ovino = ovinoRepository.findById(dto.ovelhaId()).orElseThrow(() -> new RuntimeException("Ovelha não encontrada para ciclo e cio"));

        cicloCioMapper.updateEntityFromDTO(dto, cicloCio);
        cicloCio.setOvelha(ovino);

        cicloCioRepository.save(cicloCio);

        return cicloCioMapper.toDTO(cicloCio);
    }

    public void delete(Long id) {
        CicloCio cicloCio = findCicloCioById(id);
        cicloCioRepository.deleteById(id);
    }
}
