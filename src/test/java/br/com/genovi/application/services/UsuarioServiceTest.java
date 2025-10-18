package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.UsuarioServiceImpl;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import br.com.genovi.infrastructure.mapper.FuncionarioMapper;
import br.com.genovi.infrastructure.mapper.UsuarioMapper;
import br.com.genovi.infrastructure.repositories.FuncionarioRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.genovi.domain.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private FuncionarioMapper funcionarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Funcionario funcionario;
    private Usuario usuario;
    private CreateUsuarioDTO createDTO;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId(1L);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setAtivo(true);

        createDTO = new CreateUsuarioDTO(1L, true, "teste", "teste123", true, 1L);
        usuarioDTO = new UsuarioDTO(1L, true, "teste", "teste123", true, funcionarioMapper.toDTO(funcionario));
    }

    @Test
    void shouldFindAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        List<UsuarioDTO> result = usuarioService.findAll();

        assertThat(result).hasSize(1);
        verify(usuarioRepository).findAll();
    }

    @Test
    void shouldFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.findById(1L);

        assertThat(result).isNotNull();
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenFindByIdNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.findById(1L));

        assertThat(exception.getMessage()).isEqualTo("Usuario não encontrado");
    }

    @Test
    void shouldSaveUsuario() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(usuarioMapper.toEntity(createDTO, true, funcionario)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO result = usuarioService.save(createDTO);

        assertThat(result).isNotNull();
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldThrowWhenSaveUsuarioFuncionarioNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.save(createDTO));

        assertThat(exception.getMessage()).isEqualTo("Funcionario não encontrado");
    }

    @Test
    void shouldUpdateUsuario() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toEntity(createDTO, usuario.isAtivo(), funcionario)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(any(Usuario.class))).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.update(1L, createDTO);

        assertThat(result).isNotNull();
        assertThat(usuario.getRoles()).contains(ROLE_USER);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void shouldThrowWhenUpdateUsuarioNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.update(1L, createDTO));

        assertThat(exception.getMessage()).isEqualTo("Usuario não encontrado");
    }

    @Test
    void shouldDisableUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.disable(1L);

        assertThat(usuario.isAtivo()).isFalse();
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldThrowWhenDisableUsuarioNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.disable(1L));

        assertThat(exception.getMessage()).isEqualTo("Usuario não encontrado");
    }
}
