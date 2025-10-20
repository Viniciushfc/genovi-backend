package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.DoencaServiceImpl;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.infrastructure.mapper.DoencaMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
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
class DoencaServiceTest {

    @Mock
    private DoencaRepository doencaRepository;

    @Mock
    private DoencaMapper doencaMapper;

    @InjectMocks
    private DoencaServiceImpl doencaService;

    private Doenca doenca;
    private DoencaDTO doencaDTO;
    private CreateDoencaDTO createDoencaDTO;

    @BeforeEach
    void setUp() {
        doenca = new Doenca(1L, "Febre Aftosa", "Doença viral");
        doencaDTO = new DoencaDTO(1L, "Febre Aftosa", "Doença viral");
        createDoencaDTO = new CreateDoencaDTO("Febre Aftosa", "Doença viral");
    }

    @Test
    void testFindAll() {
        // Arrange
        when(doencaRepository.findAll()).thenReturn(Collections.singletonList(doenca));
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        // Act
        List<DoencaDTO> result = doencaService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(doencaDTO, result.get(0));
        verify(doencaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        // Act
        DoencaDTO result = doencaService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(doencaDTO, result);
        verify(doencaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(doencaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doencaService.findById(1L);
        });
        assertEquals("Doença não encontrada", exception.getMessage());
        verify(doencaRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        // Arrange
        when(doencaMapper.toEntity(createDoencaDTO)).thenReturn(doenca);
        when(doencaRepository.save(doenca)).thenReturn(doenca);
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        // Act
        DoencaDTO result = doencaService.save(createDoencaDTO);

        // Assert
        assertNotNull(result);
        assertEquals(doencaDTO, result);
        verify(doencaRepository, times(1)).save(doenca);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        CreateDoencaDTO updateDto = new CreateDoencaDTO("Brucelose", "Nova descrição");
        Doenca updatedDoenca = new Doenca(1L, "Brucelose", "Nova descrição");
        DoencaDTO updatedDtoResponse = new DoencaDTO(1L, "Brucelose", "Nova descrição");

        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));
        when(doencaRepository.save(any(Doenca.class))).thenReturn(updatedDoenca);
        when(doencaMapper.toDTO(updatedDoenca)).thenReturn(updatedDtoResponse);

        // Act
        DoencaDTO result = doencaService.update(1L, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Brucelose", result.nome());
        verify(doencaRepository, times(1)).findById(1L);
        verify(doencaRepository, times(1)).save(any(Doenca.class));
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));
        doNothing().when(doencaRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> doencaService.delete(1L));

        // Assert
        verify(doencaRepository, times(1)).findById(1L);
        verify(doencaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(doencaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doencaService.delete(1L);
        });
        assertEquals("Doença não encontrada", exception.getMessage());
        verify(doencaRepository, times(1)).findById(1L);
        verify(doencaRepository, never()).deleteById(anyLong());
    }
}