package br.com.genovi.application.services;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.utils.CpfCnpjUtils;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mappers.FuncionarioMapper;
import br.com.genovi.infrastructure.repositories.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    private Funcionario findFuncionarioEntityById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado"));
    }

    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(funcionarioMapper::toDTO).toList();
    }

    public FuncionarioDTO findById(Long id) {
        return funcionarioMapper.toDTO(findFuncionarioEntityById(id));
    }

    public FuncionarioDTO save(CreateFuncionarioDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        Funcionario funcionario = funcionarioMapper.toEntity(dto);
        funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDTO(funcionario);
    }

    public FuncionarioDTO update(Long id, CreateFuncionarioDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        Funcionario funcionario = findFuncionarioEntityById(id);

        Long existingId = funcionario.getId();
        Funcionario updatedFuncionario = funcionarioMapper.toEntity(dto);
        updatedFuncionario.setId(existingId);
        funcionarioRepository.save(updatedFuncionario);
        return funcionarioMapper.toDTO(updatedFuncionario);
    }

    public void delete(Long id) {
        findFuncionarioEntityById(id);
        funcionarioRepository.deleteById(id);
    }
}
