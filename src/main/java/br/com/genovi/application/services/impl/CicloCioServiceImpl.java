package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.CicloCioService;
import br.com.genovi.domain.models.CicloCio;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mapper.CicloCioMapper;
import br.com.genovi.infrastructure.repositories.CicloCioRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CicloCioServiceImpl implements CicloCioService {

    private final CicloCioRepository cicloCioRepository;
    private final OvinoRepository ovinoRepository;
    private final CicloCioMapper cicloCioMapper;

    private CicloCio findCicloCioById(Long id) {
        if (id == null) return null;
        return cicloCioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ciclo e cio não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para CicloCio"));
    }

    @Override
    public List<CicloCioDTO> findAll() {
        return cicloCioRepository.findAll().stream().map(cicloCioMapper::toDTO).toList();
    }

    @Override
    public CicloCioDTO findById(Long id) {
        return cicloCioMapper.toDTO(findCicloCioById(id));
    }

    @Override
    public CicloCioDTO save(CreateCicloCioDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        CicloCio cicloCio = cicloCioMapper.toEntity(dto, ovino);

        cicloCioRepository.save(cicloCio);
        return cicloCioMapper.toDTO(cicloCio);
    }

    @Override
    public CicloCioDTO update(Long id, CreateCicloCioDTO dto) {
        CicloCio entity = findCicloCioById(id);
        Ovino ovino = findOvinoEntityById(dto.ovelhaId());

        entity.setOvelha(ovino);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());

        return cicloCioMapper.toDTO(cicloCioRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findCicloCioById(id);
        cicloCioRepository.deleteById(id);
    }
}
