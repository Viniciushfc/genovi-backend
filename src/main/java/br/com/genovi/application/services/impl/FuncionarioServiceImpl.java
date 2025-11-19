package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.FuncionarioService;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.utils.CpfCnpjUtils;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.FuncionarioMapper;
import br.com.genovi.infrastructure.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    private Funcionario findFuncionarioEntityById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado"));
    }

    @Override
    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(funcionarioMapper::toDTO).toList();
    }

    @Override
    public FuncionarioDTO findById(Long id) {
        return funcionarioMapper.toDTO(findFuncionarioEntityById(id));
    }

    @Override
    public FuncionarioDTO save(CreateFuncionarioDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        Funcionario entity = new Funcionario();
        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setDataAdmissao(dto.dataAdmissao());
        entity.setDataRecisao(dto.dataRecisao());

        funcionarioRepository.save(entity);
        return funcionarioMapper.toDTO(entity);
    }

    @Override
    public FuncionarioDTO update(Long id, CreateFuncionarioDTO dto) {
        if (!CpfCnpjUtils.isCpfOrCnpjValido(dto.cpfCnpj())) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        Funcionario entity = findFuncionarioEntityById(id);

        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setDataAdmissao(dto.dataAdmissao());
        entity.setDataRecisao(dto.dataRecisao());

        funcionarioRepository.save(entity);

        return funcionarioMapper.toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        findFuncionarioEntityById(id);
        funcionarioRepository.deleteById(id);
    }
}
