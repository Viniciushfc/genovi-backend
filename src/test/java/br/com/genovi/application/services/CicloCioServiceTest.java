package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.CicloCioServiceImpl;
import br.com.genovi.domain.models.CicloCio;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import br.com.genovi.infrastructure.mapper.CicloCioMapper;
import br.com.genovi.infrastructure.mapper.OvinoMapper;
import br.com.genovi.infrastructure.repositories.CicloCioRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
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

    @Mock
    private Ovino ovelha;

    @Mock
    private CicloCio cicloCio;

    @Mock
    private CicloCioDTO cicloCioDTO;

    @Mock
    private CreateCicloCioDTO createDto;

    @Mock
    private OvinoMapper ovinoMapper;

    @InjectMocks
    private CicloCioServiceImpl cicloCioService;

    @BeforeEach
    void setUp() {
        ovelha = new Ovino();
        ovelha.setId(1L);

        cicloCio = new CicloCio();
        cicloCio.setId(1L);

        cicloCioDTO = new CicloCioDTO(
                ovinoMapper.toDTO(ovelha),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5),
                "OBS TESTE");
        createDto = new CreateCicloCioDTO(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5),
                "OBS TESTE");
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
        when(cicloCioMapper.toEntity(createDto, ovelha)).thenReturn(cicloCio);
        when(cicloCioMapper.toDTO(cicloCio)).thenReturn(cicloCioDTO);

        CicloCioDTO result = cicloCioService.update(1L, createDto);

        assertNotNull(result);
        verify(cicloCioMapper).toEntity(createDto, ovelha);
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
