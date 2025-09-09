package br.com.genovi.application.services;

import br.com.genovi.domain.models.Criador;
import br.com.genovi.domain.utils.CpfCnpjUtils;
import br.com.genovi.dtos.criador.CreateCriadorDTO;
import br.com.genovi.dtos.criador.CriadorDTO;
import br.com.genovi.infrastructure.mappers.CriadorMapper;
import br.com.genovi.infrastructure.repositories.CriadorRepository;
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

    private Criador findCriadorEntityById(Long id) {
        return criadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Criador não encontrado"));
    }

    public List<CriadorDTO> findAll() {
        return criadorRepository.findAll().stream().map(criadorMapper::toDTO).toList();
    }

    public CriadorDTO findById(Long id) {
        return criadorMapper.toDTO(findCriadorEntityById(id));
    }

    public CriadorDTO save(CreateCriadorDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        Criador criador = criadorMapper.toEntity(dto);
        criadorRepository.save(criador);
        return criadorMapper.toDTO(criador);
    }

    public CriadorDTO update(Long id, CreateCriadorDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

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
