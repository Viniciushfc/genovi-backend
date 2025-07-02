package br.com.genovi.application.services;

import br.com.genovi.domain.enums.TypeUsuario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.UsuarioDTO;
import br.com.genovi.infrastructure.mappers.UsuarioMapper;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.genovi.domain.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private Usuario usuario;

    @Mock
    private UsuarioDTO usuarioDTO;

    @InjectMocks private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setAtivo(true);
        usuario.setRoles(Collections.singleton(ROLE_USER));

        usuarioDTO = new UsuarioDTO("Username Test", "e-mail@teste.com", "Senha@Test123", TypeUsuario.TRATADOR, true);
    }

    @Test
    void shouldReturnAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        List<UsuarioDTO> result = usuarioService.findAll();

        assertThat(result).containsExactly(usuarioDTO);
        verify(usuarioRepository).findAll();
    }

    @Test
    void shouldReturnUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.findById(1L);

        assertThat(result).isEqualTo(usuarioDTO);
    }

    @Test
    void shouldThrowWhenUsuarioNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario não encontrado");
    }

    @Test
    void shouldSaveUsuario() {
        when(usuarioMapper.toEntity(usuarioDTO, true)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.save(usuarioDTO);

        assertThat(result).isEqualTo(usuarioDTO);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldUpdateUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.update(1L, usuarioDTO);

        assertThat(result).isEqualTo(usuarioDTO);
        verify(usuarioMapper).updateEntityFromDTO(usuarioDTO, usuario, true);
    }

    @Test
    void shouldDisableUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.disable(1L);

        assertThat(usuario.isAtivo()).isFalse();
        verify(usuarioRepository).save(usuario);
    }
}
