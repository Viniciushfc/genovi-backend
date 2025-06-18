package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Usuario;
import br.com.genovi.application.dtos.UsuarioDTO;
import br.com.genovi.application.infrastructure.mappers.UsuarioMapper;
import br.com.genovi.application.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO save(UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        Usuario usuario = findUsuarioById(id);
        usuarioMapper.updateEntityFromDTO(dto, usuario);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public void disable(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuario.setAtivo(false);
    }
}