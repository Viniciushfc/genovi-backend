package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.OcorrenciaDoencaServiceImpl;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.application.mapper.OcorrenciaDoencaMapper;
import br.com.genovi.infrastructure.repository.DoencaRepository;
import br.com.genovi.infrastructure.repository.FuncionarioRepository;
import br.com.genovi.infrastructure.repository.OcorrenciaDoencaRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OcorrenciaDoencaServiceTest {

    @Mock
    private OcorrenciaDoencaRepository ocorrenciaDoencaRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private DoencaRepository doencaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    private Ovino ovino;

    private Doenca doenca;

    private Funcionario funcionario;

    private OcorrenciaDoenca ocorrencia;

    private CreateOcorrenciaDoencaDTO dto;

    private OcorrenciaDoencaDTO ocorrenciaDTO;

    @InjectMocks
    private OcorrenciaDoencaServiceImpl service;

    @BeforeEach
    void setUp() {
        ovino = new Ovino();
        ovino.setId(1L);

        doenca = new Doenca();
        doenca.setId(2L);

        funcionario = new Funcionario();
        funcionario.setId(3L);

        dto = new CreateOcorrenciaDoencaDTO(
                1L,
                2L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                true
        );

        ocorrencia = new OcorrenciaDoenca(
                10L,
                ovino,
                doenca,
                dto.dataInicio(),
                dto.dataFinal(),
                dto.curado()
        );
    }

    @Test
    void shouldSaveOccurrenceSuccessfully() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(doencaRepository.findById(2L)).thenReturn(Optional.of(doenca));
        when(ocorrenciaDoencaRepository.save(any(OcorrenciaDoenca.class))).thenReturn(ocorrencia);
        when(ocorrenciaDoencaMapper.toDTO(any(OcorrenciaDoenca.class))).thenReturn(ocorrenciaDTO);

        OcorrenciaDoencaDTO result = service.save(dto);

        assertThat(result).isEqualTo(ocorrenciaDTO);
        verify(ocorrenciaDoencaRepository).save(any(OcorrenciaDoenca.class));
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
