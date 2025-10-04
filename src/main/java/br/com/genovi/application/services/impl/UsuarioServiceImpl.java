package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.UsuarioService;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import br.com.genovi.infrastructure.mappers.UsuarioMapper;
import br.com.genovi.infrastructure.repositories.FuncionarioRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static br.com.genovi.domain.enums.Role.ROLE_USER;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, FuncionarioRepository funcionarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

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
        Funcionario funcionario = findFuncionarioById(dto.funcionarioId());
        Usuario usuario = findUsuarioById(id);

        Long existingId = usuario.getId();
        Usuario updatedUsuario = usuarioMapper.toEntity(dto, usuario.isAtivo(), funcionario);
        updatedUsuario.setId(existingId);
        updatedUsuario.setRoles(Collections.singleton(ROLE_USER));

        usuarioRepository.save(updatedUsuario);

        return usuarioMapper.toDTO(updatedUsuario);
    }

    @Override
    public void disable(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
