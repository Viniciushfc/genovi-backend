package br.com.genovi.application.services;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.infrastructure.mappers.PartoMapper;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.PartoRepository;
import br.com.genovi.infrastructure.repositories.ReproducaoRepository;
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
class PartoServiceTest {

    @Mock
    private PartoRepository partoRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private ReproducaoRepository reproducaoRepository;

    @Mock
    private PartoMapper partoMapper;

    @Mock
    private CreatePartoDTO createPartoDTO;

    @Mock
    private Ovino ovelhaMae;

    @Mock
    private List<Ovino> crias;

    @Mock
    private Reproducao reproducao;

    @Mock
    private Parto parto;

    @Mock
    private PartoDTO partoDTO;

    @InjectMocks
    private PartoService partoService;

    @BeforeEach
    void setup() {
        ovelhaMae = new Ovino();
        ovelhaMae.setId(1L);

        Ovino cria = new Ovino();
        cria.setId(2L);
        crias = List.of(cria);

        reproducao = new Reproducao();
        reproducao.setId(3L);

        parto = new Parto();
        parto.setId(10L);

        partoDTO = new PartoDTO(
                null,
                null,
                createPartoDTO.dataParto(),
                2,
                "Observação",
                true,
                null
        );

        createPartoDTO = new CreatePartoDTO(
                1L,
                List.of(2L),
                LocalDateTime.now(),
                2,
                "Observação",
                true,
                3L
        );
    }

    @Test
    void deveSalvarPartoComSucesso() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelhaMae));
        when(ovinoRepository.findAllById(List.of(2L))).thenReturn(crias);
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(partoMapper.toEntity(createPartoDTO, ovelhaMae, crias, reproducao)).thenReturn(parto);
        when(partoMapper.toDTO(parto)).thenReturn(partoDTO);

        PartoDTO result = partoService.save(createPartoDTO);

        verify(partoRepository).save(parto);
        assertThat(result).isEqualTo(partoDTO);
    }

    @Test
    void deveLancarExcecaoQuandoOvelhaMaeNaoForEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partoService.save(createPartoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado para Parto");
    }

    @Test
    void deveLancarExcecaoQuandoReproducaoNaoEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelhaMae));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partoService.save(createPartoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reproducao não encontrado para Parto");
    }

    @Test
    void deveLancarExcecaoQuandoAnimaisCriadosNaoForemTodosEncontrados() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelhaMae));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(ovinoRepository.findAllById(List.of(2L))).thenReturn(List.of());

        assertThatThrownBy(() -> partoService.save(createPartoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Um ou mais Animais Criados não foram encontrados.");
    }
}
