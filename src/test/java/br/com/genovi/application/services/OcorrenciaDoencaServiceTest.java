package br.com.genovi.application.services;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.infrastructure.mappers.OcorrenciaDoencaMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
import br.com.genovi.infrastructure.repositories.OcorrenciaDoencaRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcorrenciaDoencaServiceTest {

    @Mock
    private OcorrenciaDoencaRepository ocorrenciaDoencaRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private DoencaRepository doencaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    @Mock
    private Ovino ovino;

    @Mock
    private Doenca doenca;

    @Mock
    private Usuario usuario;

    @Mock
    private OcorrenciaDoenca ocorrencia;

    @Mock
    private CreateOcorrenciaDoencaDTO dto;

    @Mock
    private OcorrenciaDoencaDTO ocorrenciaDTO;

    @InjectMocks
    private OcorrenciaDoencaService service;

    @BeforeEach
    void setUp() {
        ovino = new Ovino();
        ovino.setId(1L);

        doenca = new Doenca();
        doenca.setId(2L);

        usuario = new Usuario();
        usuario.setId(3L);

        dto = new CreateOcorrenciaDoencaDTO(
                1L,
                2L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                true,
                3L
        );

        ocorrencia = new OcorrenciaDoenca(
                10L,
                ovino,
                doenca,
                dto.dataInicio(),
                dto.dataFinal(),
                dto.curado(),
                usuario
        );
    }

    @Test
    void shouldSaveOccurrenceSuccessfully() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(doencaRepository.findById(2L)).thenReturn(Optional.of(doenca));
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(usuario));
        when(ocorrenciaDoencaMapper.toEntity(dto, ovino, doenca, usuario)).thenReturn(ocorrencia);
        when(ocorrenciaDoencaMapper.toDTO(ocorrencia)).thenReturn(ocorrenciaDTO);

        OcorrenciaDoencaDTO result = service.save(dto);

        assertThat(result).isEqualTo(ocorrenciaDTO);
        verify(ocorrenciaDoencaRepository).save(ocorrencia);
    }

    @Test
    void shouldThrowExceptionWhenOvinoNotFound() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> service.save(dto));

        assertThat(exception).isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado para Ocorrencia Doença");
    }

    @Test
    void shouldReturnDTOWhenFindById() {
        when(ocorrenciaDoencaRepository.findById(10L)).thenReturn(Optional.of(ocorrencia));
        when(ocorrenciaDoencaMapper.toDTO(ocorrencia)).thenReturn(ocorrenciaDTO);

        OcorrenciaDoencaDTO result = service.findById(10L);

        assertThat(result).isEqualTo(ocorrenciaDTO);
    }

    @Test
    void shouldThrowExceptionWhenOccurrenceNotFoundById() {
        when(ocorrenciaDoencaRepository.findById(10L)).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> service.findById(10L));

        assertThat(exception).isInstanceOf(RuntimeException.class)
                .hasMessage("OcorrenciaDoenca não encontrado");
    }

    @Test
    void shouldDeleteOccurrenceWhenExists() {
        when(ocorrenciaDoencaRepository.findById(10L)).thenReturn(Optional.of(ocorrencia));

        service.delete(10L);

        verify(ocorrenciaDoencaRepository).deleteById(10L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingOccurrence() {
        when(ocorrenciaDoencaRepository.findById(10L)).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> service.delete(10L));

        assertThat(exception).isInstanceOf(RuntimeException.class)
                .hasMessage("OcorrenciaDoenca não encontrado");
    }

    @Test
    void shouldReturnListOfOccurrenceDTOs() {
        when(ocorrenciaDoencaRepository.findAll()).thenReturn(List.of(ocorrencia));
        when(ocorrenciaDoencaMapper.toDTO(ocorrencia)).thenReturn(ocorrenciaDTO);

        List<OcorrenciaDoencaDTO> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).contains(ocorrenciaDTO);
    }

}
