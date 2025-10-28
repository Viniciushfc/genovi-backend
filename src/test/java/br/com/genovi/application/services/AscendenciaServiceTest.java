package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.AscendenciaServiceImpl;
import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import br.com.genovi.application.mapper.AscendenciaMapper;
import br.com.genovi.application.mapper.OvinoMapper;
import br.com.genovi.infrastructure.repository.AscendenciaRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AscendenciaServiceTest {

    @Mock
    private AscendenciaRepository ascendenciaRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private AscendenciaMapper ascendenciaMapper;

    @Mock
    private Ascendencia ascendencia;

    @Mock
    private Ovino pai;

    @Mock
    private Ovino mae;

    @Mock
    private AscendenciaDTO ascendenciaDTO;

    @Mock
    private CreateAscendenciaDTO createDto;

    @Mock
    private OvinoMapper ovinoMapper;

    @InjectMocks
    private AscendenciaServiceImpl ascendenciaService;


    @BeforeEach
    void setUp() {
        pai = new Ovino();
        pai.setId(1L);

        mae = new Ovino();
        mae.setId(2L);

        ascendencia = new Ascendencia();
        ascendencia.setId(3L);
        ascendencia.setPai(pai);
        ascendencia.setMae(mae);

        ascendenciaDTO = new AscendenciaDTO(
                1L,
                ovinoMapper.toDTO(pai),
                ovinoMapper.toDTO(mae));
        createDto = new CreateAscendenciaDTO(
                1L, 2L
        );
    }

    @Test
    void findAllDeveRetornarListaDeDTOs() {
        when(ascendenciaRepository.findAll()).thenReturn(List.of(ascendencia));
        when(ascendenciaMapper.toDTO(ascendencia)).thenReturn(ascendenciaDTO);

        List<AscendenciaDTO> result = ascendenciaService.findAll();

        assertEquals(1, result.size());
        verify(ascendenciaRepository).findAll();
        verify(ascendenciaMapper).toDTO(ascendencia);
    }

    @Test
    void findByIdDeveRetornarDTOQuandoEncontrado() {
        when(ascendenciaRepository.findById(3L)).thenReturn(Optional.of(ascendencia));
        when(ascendenciaMapper.toDTO(ascendencia)).thenReturn(ascendenciaDTO);

        AscendenciaDTO result = ascendenciaService.findById(3L);


        assertNotNull(result);
        verify(ascendenciaRepository).findById(3L);
    }

    @Test
    void findByIdDeveLancarExcecaoQuandoNaoEncontrado() {
        when(ascendenciaRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ascendenciaService.findById(3L));
    }

    @Test
    void saveDeveSalvarEMapearDTO() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(pai));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(mae));
        when(ascendenciaMapper.toEntity(createDto, pai, mae)).thenReturn(ascendencia);
        when(ascendenciaMapper.toDTO(ascendencia)).thenReturn(ascendenciaDTO);

        AscendenciaDTO result = ascendenciaService.save(createDto);

        assertNotNull(result);
        verify(ascendenciaRepository).save(ascendencia);
    }

    @Test
    void saveDeveLancarExcecaoSePaiNaoEncontrado() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ascendenciaService.save(createDto));
    }

    @Test
    void saveDeveLancarExcecaoSeMaeNaoEncontrado() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(pai));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ascendenciaService.save(createDto));
    }

    @Test
    void updateDeveAtualizarEMapearDTO() {
        when(ascendenciaRepository.findById(3L)).thenReturn(Optional.of(ascendencia));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(pai));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(mae));
        when(ascendenciaRepository.save(ascendencia)).thenReturn(ascendencia);
        when(ascendenciaMapper.toDTO(ascendencia)).thenReturn(ascendenciaDTO);

        AscendenciaDTO result = ascendenciaService.update(3L, createDto);

        assertNotNull(result);
        verify(ascendenciaRepository).save(ascendencia);
    }

    @Test
    void deleteDeveRemoverQuandoEncontrado() {
        when(ascendenciaRepository.findById(3L)).thenReturn(Optional.of(ascendencia));

        ascendenciaService.delete(3L);

        verify(ascendenciaRepository).deleteById(3L);
    }

    @Test
    void deleteDeveLancarExcecaoQuandoNaoEncontrado() {
        when(ascendenciaRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ascendenciaService.delete(3L));
    }
}