package br.com.genovi.application.services;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Criador;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.infrastructure.mappers.OvinoMapper;
import br.com.genovi.infrastructure.repositories.AscendenciaRepository;
import br.com.genovi.infrastructure.repositories.CriadorRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OvinoServiceTest {

    @Mock private OvinoRepository ovinoRepository;
    @Mock private AscendenciaRepository ascendenciaRepository;
    @Mock private CriadorRepository criadorRepository;
    @Mock private OvinoMapper ovinoMapper;

    @InjectMocks private OvinoService ovinoService;

    private Ovino ovino;
    private Criador criador;
    private Ascendencia ascendencia;
    private CreateOvinoDTO dto;
    private OvinoDTO ovinoDTO;

    @BeforeEach
    void setup() {
        criador = new Criador();
        criador.setId(1L);

        ascendencia = new Ascendencia();
        ascendencia.setId(1L);

        ovino = new Ovino();
        ovino.setId(1L);
        ovino.setAtivo(true);

        dto = new CreateOvinoDTO(1111L,"Nome", "Raca", "FBB", LocalDateTime.now(),1L, 2, TypeGrauPureza.PURO_ORIGEM, TypeSexo.MACHO, 40.00F,"Comportamento", 1L, TypeStatus.ATIVO);
        ovinoDTO = new OvinoDTO(1111L,true,"Nome", "Raca", "FBB", LocalDateTime.now(),criador, 2, TypeGrauPureza.PURO_ORIGEM, TypeSexo.MACHO, 40.00F,"Comportamento", ascendencia, TypeStatus.ATIVO);
    }

    @Test
    void shouldReturnAllOvinos() {
        when(ovinoRepository.findAll()).thenReturn(List.of(ovino));
        when(ovinoMapper.toDTO(ovino)).thenReturn(ovinoDTO);

        List<OvinoDTO> result = ovinoService.findAll();

        assertThat(result).containsExactly(ovinoDTO);
        verify(ovinoRepository).findAll();
    }

    @Test
    void shouldReturnOvinoById() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(ovinoMapper.toDTO(ovino)).thenReturn(ovinoDTO);

        OvinoDTO result = ovinoService.findById(1L);

        assertThat(result).isEqualTo(ovinoDTO);
    }

    @Test
    void shouldThrowWhenOvinoNotFound() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ovinoService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado");
    }

    @Test
    void shouldSaveNewOvino() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        when(ascendenciaRepository.findById(1L)).thenReturn(Optional.of(ascendencia));
        when(ovinoMapper.toEntity(dto, true, criador, ascendencia)).thenReturn(ovino);
        when(ovinoMapper.toDTO(ovino)).thenReturn(ovinoDTO);

        OvinoDTO result = ovinoService.save(dto);

        assertThat(result).isEqualTo(ovinoDTO);
        verify(ovinoRepository).save(ovino);
    }

    @Test
    void shouldUpdateOvino() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        when(ascendenciaRepository.findById(1L)).thenReturn(Optional.of(ascendencia));
        when(ovinoMapper.toDTO(ovino)).thenReturn(ovinoDTO);

        OvinoDTO result = ovinoService.update(1L, dto);

        assertThat(result).isEqualTo(ovinoDTO);
        verify(ovinoMapper).updateEntityFromDTO(dto, true, ovino, criador, ascendencia);
        verify(ovinoRepository).save(ovino);
    }

    @Test
    void shouldDisableOvino() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));

        ovinoService.disable(1L);

        assertThat(ovino.isAtivo()).isFalse();
        verify(ovinoRepository).save(ovino);
    }

    @Test
    void shouldThrowWhenCriadorNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ovinoService.save(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Criador não encontrado");
    }

    @Test
    void shouldThrowWhenAscendenciaNotFound() {
        when(criadorRepository.findById(1L)).thenReturn(Optional.of(criador));
        when(ascendenciaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ovinoService.save(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ascendência não encontrada");
    }
}
