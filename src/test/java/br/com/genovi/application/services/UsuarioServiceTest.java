package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.UsuarioServiceImpl;
import br.com.genovi.domain.enums.EnumRole;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import br.com.genovi.application.mapper.FuncionarioMapper;
import br.com.genovi.application.mapper.UsuarioMapper;
import br.com.genovi.infrastructure.repository.FuncionarioRepository;
import br.com.genovi.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.genovi.domain.enums.EnumRole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Mock
    private PasswordEncoder passwordEncoder;

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
        usuario.setFuncionario(funcionario);
        usuario.setEmail("teste");
        usuario.setSenha("encodedPassword");
        usuario.setAutenticacao2fa(true);
        usuario.setEnumRoles(Set.of(ROLE_USER));


        createDTO = new CreateUsuarioDTO(1L, true, "teste", "teste123", true, Set.of(ROLE_USER), 1L);
        usuarioDTO = new UsuarioDTO(1L, true, "teste", "teste123", true, Set.of(ROLE_USER), funcionarioMapper.toDTO(funcionario));
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
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(any(Usuario.class))).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.save(createDTO);

        assertThat(result).isNotNull();
        verify(usuarioRepository).save(any(Usuario.class));
        verify(passwordEncoder).encode("teste123");
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
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(any(Usuario.class))).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.update(1L, createDTO);

        assertThat(result).isNotNull();
        verify(usuarioRepository).save(usuario);
        verify(passwordEncoder).encode("teste123");
    }

    @Test
    void shouldThrowWhenUpdateUsuarioNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

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
