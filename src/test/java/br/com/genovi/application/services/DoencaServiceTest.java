package br.com.genovi.application.services;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.infrastructure.mappers.DoencaMapper;
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
    private DoencaService doencaService;

    private Doenca doenca;
    private CreateDoencaDTO createDoencaDTO;
    private DoencaDTO doencaDTO;

    @BeforeEach
    void setUp() {
        doenca = new Doenca();
        doenca.setId(1L);
        doenca.setNome("Nome teste");
        doenca.setDescricao("Descrição Teste");

        createDoencaDTO = new CreateDoencaDTO(
                "Nome teste",
                "Descrição Teste"
        );

        doencaDTO = new DoencaDTO(
                1L,
                "Nome teste",
                "Descrição Teste"
        );
    }

    @Test
    void testFindAllSuccess() {
        when(doencaRepository.findAll()).thenReturn(Collections.singletonList(doenca));
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        List<DoencaDTO> result = doencaService.findAll();

        assertEquals(1, result.size());
        assertEquals("Nome teste", result.get(0).nome());
        verify(doencaRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        DoencaDTO result = doencaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(doencaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> doencaService.findById(1L));
        assertEquals("Doença não encontrada", exception.getMessage());
        verify(doencaRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveSuccess() {
        when(doencaMapper.toEntity(createDoencaDTO)).thenReturn(doenca);
        when(doencaRepository.save(doenca)).thenReturn(doenca);
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        DoencaDTO result = doencaService.save(createDoencaDTO);

        assertNotNull(result);
        assertEquals("Nome teste", result.nome());
        verify(doencaRepository, times(1)).save(doenca);
    }

    @Test
    void testUpdateSuccess() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));
        doNothing().when(doencaMapper).updateEntityFromDTO(createDoencaDTO, doenca);
        when(doencaRepository.save(doenca)).thenReturn(doenca);
        when(doencaMapper.toDTO(doenca)).thenReturn(doencaDTO);

        DoencaDTO result = doencaService.update(1L, createDoencaDTO);

        assertNotNull(result);
        assertEquals("Nome teste", result.nome());
        verify(doencaRepository, times(1)).save(doenca);
    }

    @Test
    void testUpdateNotFound() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> doencaService.update(1L, createDoencaDTO));
        assertEquals("Doença não encontrada", exception.getMessage());
        verify(doencaRepository, times(1)).findById(1L);
        verify(doencaRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.of(doenca));

        doencaService.delete(1L);

        verify(doencaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(doencaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> doencaService.delete(1L));
        assertEquals("Doença não encontrada", exception.getMessage());
        verify(doencaRepository, times(1)).findById(1L);
        verify(doencaRepository, never()).deleteById(anyLong());
    }
}
