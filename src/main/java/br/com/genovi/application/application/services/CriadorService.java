package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Criador;
import br.com.genovi.application.dtos.CriadorDTO;
import br.com.genovi.application.infrastructure.mappers.CriadorMapper;
import br.com.genovi.application.infrastructure.repositories.CriadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriadorService {

    private final CriadorRepository criadorRepository;
    private final CriadorMapper criadorMapper;

    public CriadorService(CriadorRepository criadorRepository, CriadorMapper criadorMapper) {
        this.criadorRepository = criadorRepository;
        this.criadorMapper = criadorMapper;
    }

    public Criador findCriadorEntityById(Long id) {
        return criadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Criador não encontrado"));
    }

    public List<CriadorDTO> findAll() {
        return criadorRepository.findAll().stream().map(criadorMapper::toDTO).toList();
    }

    public CriadorDTO findById(Long id) {
        Criador criador = criadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Criador não encontrado"));
        return criadorMapper.toDTO(criador);
    }

    public CriadorDTO save(CriadorDTO dto) {
        Criador criador = criadorMapper.toEntity(dto);
        criadorRepository.save(criador);
        return criadorMapper.toDTO(criador);
    }

    public CriadorDTO update(Long id, CriadorDTO dto) {
        Criador criador = findCriadorEntityById(id);
        criadorMapper.updateEntityFromDTO(dto, criador);
        criadorRepository.save(criador);
        return criadorMapper.toDTO(criador);
    }

    public void delete(Long id) {
        findCriadorEntityById(id);
        criadorRepository.deleteById(id);
    }
}
