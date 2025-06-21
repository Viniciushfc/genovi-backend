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

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado para CicloCio"));
    }

    public List<CicloCioDTO> findAll() {
        return cicloCioRepository.findAll().stream().map(cicloCioMapper::toDTO).toList();
    }

    public CicloCioDTO findById(Long id) {
        return cicloCioMapper.toDTO(findCicloCioById(id));
    }

    public CicloCioDTO save(CreateCicloCioDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        CicloCio cicloCio = cicloCioMapper.toEntity(dto, ovino);

        cicloCioRepository.save(cicloCio);
        return cicloCioMapper.toDTO(cicloCio);
    }

    public CicloCioDTO update(Long id, CreateCicloCioDTO dto) {
        CicloCio cicloCio = findCicloCioById(id);
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        cicloCioMapper.updateEntityFromDTO(dto, cicloCio, ovino);
        cicloCio.setOvelha(ovino);

        cicloCioRepository.save(cicloCio);

        return cicloCioMapper.toDTO(cicloCio);
    }

    public void delete(Long id) {
        findCicloCioById(id);
        cicloCioRepository.deleteById(id);
    }
}
