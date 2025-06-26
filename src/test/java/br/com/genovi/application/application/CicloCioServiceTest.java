package br.com.genovi.application.application;

import br.com.genovi.application.application.services.CicloCioService;
import br.com.genovi.application.domain.models.CicloCio;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.CicloCioDTO;
import br.com.genovi.application.dtos.CreateCicloCioDTO;
import br.com.genovi.application.infrastructure.mappers.CicloCioMapper;
import br.com.genovi.application.infrastructure.repositories.CicloCioRepository;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CicloCioServiceTest {

    @Mock
    private CicloCioRepository cicloCioRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private CicloCioMapper cicloCioMapper;

    @InjectMocks
    private CicloCioService cicloCioService;

    private Ovino ovelha;
    private CicloCio cicloCio;
    private CicloCioDTO cicloCioDTO;
    private CreateCicloCioDTO createDto;

    @BeforeEach
    void setUp() {
        ovelha = new Ovino();
        ovelha.setId(1L);

        cicloCio = new CicloCio();
        cicloCio.setId(1L);

        cicloCioDTO = new CicloCioDTO(ovelha, LocalDateTime.of(2024, 1, 1, 10, 0), LocalDateTime.of(2024, 1, 2, 10, 0), "OBS TESTE");
        createDto = new CreateCicloCioDTO(1L, LocalDateTime.of(2024, 1, 1, 10, 0), LocalDateTime.of(2024, 2, 1, 10, 0), "OBS TESTE");
    }

    @Test
    void findAllDeveRetornarListaDeDTOs() {
        when(cicloCioRepository.findAll()).thenReturn(List.of(cicloCio));
        when(cicloCioMapper.toDTO(cicloCio)).thenReturn(cicloCioDTO);

        List<CicloCioDTO> result = cicloCioService.findAll();

        assertEquals(1, result.size());
        assertEquals(cicloCioDTO, result.get(0));
        verify(cicloCioRepository).findAll();
        verify(cicloCioMapper).toDTO(cicloCio);
    }

    @Test
    void findByIdDeveRetornarDTOQuandoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.of(cicloCio));
        when(cicloCioMapper.toDTO(cicloCio)).thenReturn(cicloCioDTO);

        CicloCioDTO result = cicloCioService.findById(1L);

        assertEquals(cicloCioDTO, result);
        verify(cicloCioRepository).findById(1L);
    }

    @Test
    void findByIdDeveLancarExcecaoSeNaoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cicloCioService.findById(1L));
    }

    @Test
    void saveDeveSalvarCicloCioEMapearDTO() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelha));
        when(cicloCioMapper.toEntity(createDto, ovelha)).thenReturn(cicloCio);
        when(cicloCioMapper.toDTO(cicloCio)).thenReturn(cicloCioDTO);

        CicloCioDTO result = cicloCioService.save(createDto);

        assertNotNull(result);
        verify(cicloCioRepository).save(cicloCio);
        verify(cicloCioMapper).toEntity(createDto, ovelha);
        verify(cicloCioMapper).toDTO(cicloCio);
    }

    @Test
    void saveDeveLancarExcecaoSeOvelhaNaoEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cicloCioService.save(createDto));
    }

    @Test
    void updateDeveAtualizarCicloCioQuandoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.of(cicloCio));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelha));
        doNothing().when(cicloCioMapper).updateEntityFromDTO(createDto, cicloCio, ovelha);
        when(cicloCioMapper.toDTO(cicloCio)).thenReturn(cicloCioDTO);

        CicloCioDTO result = cicloCioService.update(1L, createDto);

        assertNotNull(result);
        verify(cicloCioMapper).updateEntityFromDTO(createDto, cicloCio, ovelha);
        verify(cicloCioRepository).save(cicloCio);
    }

    @Test
    void updateDeveLancarExcecaoSeCicloNaoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cicloCioService.update(1L, createDto));
    }

    @Test
    void updateDeveLancarExcecaoSeOvelhaNaoEncontrada() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.of(cicloCio));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cicloCioService.update(1L, createDto));
    }

    @Test
    void deleteDeveRemoverQuandoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.of(cicloCio));

        cicloCioService.delete(1L);

        verify(cicloCioRepository).deleteById(1L);
    }

    @Test
    void deleteDeveLancarExcecaoSeNaoEncontrado() {
        when(cicloCioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cicloCioService.delete(1L));
    }
}
