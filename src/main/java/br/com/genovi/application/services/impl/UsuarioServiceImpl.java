package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.UsuarioService;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import br.com.genovi.infrastructure.mapper.UsuarioMapper;
import br.com.genovi.infrastructure.repositories.FuncionarioRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static br.com.genovi.domain.enums.Role.ROLE_USER;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioMapper usuarioMapper;

    private Funcionario findFuncionarioById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado"));
    }

    private Usuario findUsuarioById(Long id) {
        if (id == null) return null;
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    @Override
    public UsuarioDTO findById(Long id) {
        return usuarioMapper.toDTO(findUsuarioById(id));
    }

    @Override
    public UsuarioDTO save(CreateUsuarioDTO dto) {
        Funcionario funcionario = findFuncionarioById(dto.funcionarioId());

        Usuario usuario = usuarioMapper.toEntity(dto, true, funcionario);

        usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioDTO update(Long id, CreateUsuarioDTO dto) {
        Usuario entity = findUsuarioById(id);
        Funcionario funcionario = findFuncionarioById(dto.funcionarioId());

        entity.setFuncionario(funcionario);
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setAutenticacao2fa(dto.autenticacao2fa());
        entity.setRoles(Collections.singleton(ROLE_USER));

        return usuarioMapper.toDTO(usuarioRepository.save(entity));
    }

    @Override
    public void disable(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
