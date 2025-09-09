package br.com.genovi.application.services;


import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import br.com.genovi.infrastructure.mappers.UsuarioMapper;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static br.com.genovi.domain.enums.Role.ROLE_USER;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    public UsuarioDTO findById(Long id) {
        return usuarioMapper.toDTO(findUsuarioById(id));
    }

    public UsuarioDTO save(CreateUsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto, true);

        usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO update(Long id, CreateUsuarioDTO dto) {
        Usuario usuario = findUsuarioById(id);

        usuarioMapper.updateEntityFromDTO(dto, usuario, true);
        usuario.setRoles(Collections.singleton(ROLE_USER));

        return usuarioMapper.toDTO(usuario);
    }

    public void disable(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}