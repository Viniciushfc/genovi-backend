package br.com.genovi.application.services;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.infrastructure.mappers.FuncionarioMapper;
import br.com.genovi.infrastructure.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private FuncionarioMapper funcionarioMapper;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private FuncionarioDTO funcionarioDTO;
    private CreateFuncionarioDTO createFuncionarioDTO;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Nome Teste");
        funcionario.setCpfCnpj("52998224725");
        funcionario.setEndereco("endereco teste");
        funcionario.setTelefone("11999999999");
        funcionario.setDataAdmissao(LocalDateTime.now());

        createFuncionarioDTO = new CreateFuncionarioDTO(
                "Nome Teste",
                "52998224725",
                "endereco teste",
                "11999999999",
                null,
                null
        );

        funcionarioDTO = new FuncionarioDTO(
                1L,
                "Nome Teste",
                "52998224725",
                "endereco teste",
                "11999999999",
                funcionario.getDataAdmissao(),
                null
        );
    }

    @Test
    void testFindAll() {
        when(funcionarioRepository.findAll()).thenReturn(Collections.singletonList(funcionario));
        when(funcionarioMapper.toDTO(funcionario)).thenReturn(funcionarioDTO);

        List<FuncionarioDTO> result = funcionarioService.findAll();

        assertThat(result).hasSize(1).containsExactly(funcionarioDTO);
        verify(funcionarioRepository).findAll();
    }

    @Test
    void testFindById() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(funcionarioMapper.toDTO(funcionario)).thenReturn(funcionarioDTO);

        FuncionarioDTO result = funcionarioService.findById(1L);

        assertThat(result).isEqualTo(funcionarioDTO);
        verify(funcionarioRepository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> funcionarioService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Funcionario não encontrado");

        verify(funcionarioRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(funcionarioMapper.toEntity(createFuncionarioDTO)).thenReturn(funcionario);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        when(funcionarioMapper.toDTO(funcionario)).thenReturn(funcionarioDTO);

        FuncionarioDTO result = funcionarioService.save(createFuncionarioDTO);

        assertThat(result).isEqualTo(funcionarioDTO);
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    void testUpdate() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(funcionarioMapper.toEntity(createFuncionarioDTO)).thenReturn(funcionario);
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);
        when(funcionarioMapper.toDTO(funcionario)).thenReturn(funcionarioDTO);

        FuncionarioDTO result = funcionarioService.update(1L, createFuncionarioDTO);

        assertThat(result).isEqualTo(funcionarioDTO);
        verify(funcionarioMapper).toEntity(createFuncionarioDTO);
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    void testUpdateNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> funcionarioService.update(1L, createFuncionarioDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Funcionario não encontrado");

        verify(funcionarioRepository).findById(1L);
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        funcionarioService.delete(1L);

        verify(funcionarioRepository).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> funcionarioService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Funcionario não encontrado");

        verify(funcionarioRepository).findById(1L);
        verify(funcionarioRepository, never()).deleteById(any());
    }
}
