package br.com.genovi.application.services;

import br.com.genovi.domain.models.Criador;
import br.com.genovi.dtos.CriadorDTO;
import br.com.genovi.infrastructure.mappers.CriadorMapper;
import br.com.genovi.infrastructure.repositories.CriadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private Criador criador;

    @Mock
    private CriadorDTO criadorDTO;

    @InjectMocks
    private CriadorService criadorService;

    @BeforeEach
    void setUp() {
        criador = new Criador();
        criador.setId(1L);

        criadorDTO = new CriadorDTO(
                "Nome Teste",
                "99999999999",
                "endereco teste",
                "99999999999");
    }

    @Test
    void testFindAll() {
        when(criadorRepository.findAll()).thenReturn(Collections.singletonList(criador));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        List<CriadorDTO> result = criadorService.findAll();

        assertEquals(1, result.size());
        verify(criadorRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.findById(1L);

        assertNotNull(result);
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
    void testSave() {
        when(criadorMapper.toEntity(criadorDTO)).thenReturn(criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.save(criadorDTO);

        assertNotNull(result);
        verify(criadorRepository, times(1)).save(criador);
    }

    @Test
    void testUpdate() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        doNothing().when(criadorMapper).updateEntityFromDTO(criadorDTO, criador);
        when(criadorMapper.toDTO(criador)).thenReturn(criadorDTO);

        CriadorDTO result = criadorService.update(1L, criadorDTO);

        assertNotNull(result);
        verify(criadorRepository, times(1)).save(criador);
    }

    @Test
    void testUpdateNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> criadorService.update(1L, criadorDTO));
        assertEquals("Criador não encontrado", exception.getMessage());
        verify(criadorRepository, times(1)).findById(1L);
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
}
