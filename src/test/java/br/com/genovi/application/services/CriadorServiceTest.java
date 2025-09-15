package br.com.genovi.application.services;

import br.com.genovi.domain.models.Criador;
import br.com.genovi.dtos.criador.CreateCriadorDTO;
import br.com.genovi.dtos.criador.CriadorDTO;
import br.com.genovi.infrastructure.mappers.CriadorMapper;
import br.com.genovi.infrastructure.repositories.CriadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriadorServiceTest {

    @Mock
    private CriadorRepository criadorRepository;

    @Mock
    private CriadorMapper criadorMapper;

    @InjectMocks
    private CriadorService criadorService;

    private Criador criador;
    private CriadorDTO criadorDTO;
    private CreateCriadorDTO createCriadorDTO;

    @BeforeEach
    void setUp() {
        criador = new Criador();
        criador.setId(1L);
        criador.setNome("Nome Teste");
        criador.setCpfCnpj("52998224725");
        criador.setEndereco("endereco teste");
        criador.setTelefone("11999999999");

        createCriadorDTO = new CreateCriadorDTO(
                "Nome Teste",
                "52998224725",
                "endereco teste",
                "11999999999"
        );

        criadorDTO = new CriadorDTO(
                1L,
                "Nome Teste",
                "52998224725",
                "endereco teste",
                "11999999999"
        );
    }


    @Test
    void testFindAll() {
        when(criadorRepository.findAll()).thenReturn(Collections.singletonList(criador));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        List<CriadorDTO> result = criadorService.findAll();

        assertEquals(1, result.size());
        assertEquals("Nome Teste", result.get(0).nome());
        verify(criadorRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmptyList() {
        when(criadorRepository.findAll()).thenReturn(Collections.emptyList());

        List<CriadorDTO> result = criadorService.findAll();

        assertTrue(result.isEmpty());
        verify(criadorRepository, times(1)).findAll();
    }

    @Test
    void testFindAllMultipleCriadores() {
        Criador criador2 = new Criador();
        criador2.setId(2L);
        criador2.setNome("Segundo Nome");

        CriadorDTO criadorDTO2 = new CriadorDTO(2L, "Segundo Nome", "88888888888", "endereco 2", "88888888888");

        when(criadorRepository.findAll()).thenReturn(Arrays.asList(criador, criador2));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);
        when(criadorMapper.toDTO(criador2)).thenReturn(criadorDTO2);

        List<CriadorDTO> result = criadorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Nome Teste", result.get(0).nome());
        assertEquals("Segundo Nome", result.get(1).nome());
        verify(criadorRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(criadorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> criadorService.findById(1L));
        assertEquals("Criador não encontrado", exception.getMessage());
        verify(criadorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdDifferentId() {
        Long differentId = 5L;
        when(criadorRepository.findById(differentId)).thenReturn(Optional.of(criador));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.findById(differentId);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(criadorRepository, times(1)).findById(differentId);
    }

    @Test
    void testSave() {
        when(criadorMapper.toEntity(createCriadorDTO)).thenReturn(criador);
        when(criadorRepository.save(criador)).thenReturn(criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.save(createCriadorDTO);

        assertNotNull(result);
        assertEquals("Nome Teste", result.nome());
        verify(criadorRepository, times(1)).save(criador);
    }

    @Test
    void testSaveVerifyAllFields() {
        when(criadorMapper.toEntity(createCriadorDTO)).thenReturn(criador);
        when(criadorRepository.save(criador)).thenReturn(criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.save(createCriadorDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Nome Teste", result.nome());
        assertEquals("52998224725", result.cpfCnpj());
        assertEquals("endereco teste", result.endereco());
        assertEquals("11999999999", result.telefone());
        verify(criadorMapper, times(1)).toEntity(createCriadorDTO);
        verify(criadorRepository, times(1)).save(criador);
        verify(criadorMapper, times(1)).toDTO(criador);
    }

    @Test
    void testUpdate() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        doNothing().when(criadorMapper).updateEntityFromDTO(createCriadorDTO, criador);
        when(criadorRepository.save(criador)).thenReturn(criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.update(1L, createCriadorDTO);

        assertNotNull(result);
        assertEquals("Nome Teste", result.nome());
        verify(criadorRepository, times(1)).save(criador);
    }

    @Test
    void testUpdateNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> criadorService.update(1L, createCriadorDTO));
        assertEquals("Criador não encontrado", exception.getMessage());
        verify(criadorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateVerifyAllInteractions() {
        Long criadorId = 2L;
        when(criadorRepository.findById(criadorId)).thenReturn(Optional.of(criador));
        doNothing().when(criadorMapper).updateEntityFromDTO(createCriadorDTO, criador);
        when(criadorRepository.save(criador)).thenReturn(criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.update(criadorId, createCriadorDTO);

        assertNotNull(result);
        verify(criadorRepository, times(1)).findById(criadorId);
        verify(criadorMapper, times(1)).updateEntityFromDTO(createCriadorDTO, criador);
        verify(criadorRepository, times(1)).save(criador);
        verify(criadorMapper, times(1)).toDTO(criador);
    }

    @Test
    void testDelete() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));

        criadorService.delete(1L);

        verify(criadorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> criadorService.delete(1L));
        assertEquals("Criador não encontrado", exception.getMessage());
        verify(criadorRepository, times(1)).findById(1L);
        verify(criadorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteDifferentId() {
        Long differentId = 10L;
        when(criadorRepository.findById(differentId)).thenReturn(Optional.of(criador));

        criadorService.delete(differentId);

        verify(criadorRepository, times(1)).findById(differentId);
        verify(criadorRepository, times(1)).deleteById(differentId);
    }
}