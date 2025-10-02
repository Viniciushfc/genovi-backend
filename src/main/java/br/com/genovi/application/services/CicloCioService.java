package br.com.genovi.application.services;

import br.com.genovi.domain.models.CicloCio;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.utils.DateValidationUtils;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mappers.CicloCioMapper;
import br.com.genovi.infrastructure.repositories.CicloCioRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
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
        if (id == null) return null;
        return cicloCioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ciclo e cio não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para CicloCio"));
    }

    public List<CicloCioDTO> findAll() {
        return cicloCioRepository.findAll().stream().map(cicloCioMapper::toDTO).toList();
    }

    public CicloCioDTO findById(Long id) {
        return cicloCioMapper.toDTO(findCicloCioById(id));
    }

    public CicloCioDTO save(CreateCicloCioDTO dto) {
        DateValidationUtils.validarPeriodo(dto.dataInicio(), dto.dataFim());

        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        CicloCio cicloCio = cicloCioMapper.toEntity(dto, ovino);

        cicloCioRepository.save(cicloCio);
        return cicloCioMapper.toDTO(cicloCio);
    }

    public CicloCioDTO update(Long id, CreateCicloCioDTO dto) {
        DateValidationUtils.validarPeriodo(dto.dataInicio(), dto.dataFim());

        CicloCio cicloCio = findCicloCioById(id);
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        Long existingId = cicloCio.getId();
        CicloCio updatedCicloCio = cicloCioMapper.toEntity(dto, ovino);
        updatedCicloCio.setId(existingId);
        cicloCioRepository.save(updatedCicloCio);

        return cicloCioMapper.toDTO(updatedCicloCio);
    }

    public void delete(Long id) {
        findCicloCioById(id);
        cicloCioRepository.deleteById(id);
    }
}
