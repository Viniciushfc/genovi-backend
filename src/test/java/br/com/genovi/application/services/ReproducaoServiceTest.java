package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.ReproducaoServiceImpl;
import br.com.genovi.domain.enums.EnumReproducao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import br.com.genovi.application.mapper.ReproducaoMapper;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.ReproducaoRepository;
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
class ReproducaoServiceTest {

    @Mock
    private ReproducaoRepository reproducaoRepository;
    @Mock
    private OvinoRepository ovinoRepository;
    @Mock
    private ReproducaoMapper reproducaoMapper;

    @Mock
    private Reproducao reproducao;

    private ReproducaoDTO reproducaoDTO;

    @Mock
    private CreateReproducaoDTO createReproducaoDTO;

    private Ovino carneiro;

    private Ovino ovelha;

    @InjectMocks
    private ReproducaoServiceImpl reproducaoService;

    @BeforeEach
    void setUp() {
        carneiro = new Ovino();
        carneiro.setId(1L);

        ovelha = new Ovino();
        ovelha.setId(2L);

        reproducao = new Reproducao();
        reproducao.setId(1L);

        OvinoResumeDTO carneiroResumo = new OvinoResumeDTO(carneiro.getId(), carneiro.getRfid(), carneiro.getNome(), carneiro.getFbb());
        OvinoResumeDTO ovelhaResumo = new OvinoResumeDTO(ovelha.getId(), ovelha.getRfid(), ovelha.getNome(), ovelha.getFbb());
        reproducaoDTO = new ReproducaoDTO(1L, LocalDateTime.now(), carneiroResumo, ovelhaResumo, EnumReproducao.INSEMINACAO_ARTIFICIAL);

        createReproducaoDTO = new CreateReproducaoDTO(LocalDateTime.now(), 1L, 2L, EnumReproducao.INSEMINACAO_ARTIFICIAL);
    }

    @Test
    void shouldReturnAllReproducoes() {
        when(reproducaoRepository.findAll()).thenReturn(List.of(reproducao));
        when(reproducaoMapper.toDTO(reproducao)).thenReturn(reproducaoDTO);

        List<ReproducaoDTO> result = reproducaoService.findAll();

        assertThat(result).containsExactly(reproducaoDTO);
        verify(reproducaoRepository).findAll();
    }

    @Test
    void shouldReturnReproducaoById() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.of(reproducao));
        when(reproducaoMapper.toDTO(reproducao)).thenReturn(reproducaoDTO);

        ReproducaoDTO result = reproducaoService.findById(1L);

        assertThat(result).isEqualTo(reproducaoDTO);
    }

    @Test
    void shouldThrowWhenReproducaoNotFoundById() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reproducaoService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reproducao não encontrado");
    }

    @Test
    void shouldSaveReproducao() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(carneiro));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovelha));
        when(reproducaoMapper.toEntity(createReproducaoDTO, carneiro, ovelha)).thenReturn(reproducao);
        when(reproducaoRepository.save(reproducao)).thenReturn(reproducao);
        when(reproducaoMapper.toDTO(reproducao)).thenReturn(reproducaoDTO);

        ReproducaoDTO result = reproducaoService.save(createReproducaoDTO);

        assertThat(result).isEqualTo(reproducaoDTO);
        verify(reproducaoRepository).save(reproducao);
    }

    @Test
    void shouldThrowWhenCarneiroNotFoundOnSave() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reproducaoService.save(createReproducaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado");
    }

    @Test
    void shouldThrowWhenOvelhaNotFoundOnSave() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(carneiro));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reproducaoService.save(createReproducaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado");
    }

    @Test
    void shouldUpdateReproducao() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.of(reproducao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(carneiro));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovelha));
        when(reproducaoRepository.save(any(Reproducao.class))).thenReturn(reproducao);
        when(reproducaoMapper.toDTO(any(Reproducao.class))).thenReturn(reproducaoDTO);

        ReproducaoDTO result = reproducaoService.update(1L, createReproducaoDTO);

        assertThat(result).isEqualTo(reproducaoDTO);
        verify(reproducaoRepository).save(any(Reproducao.class));
    }

    @Test
    void shouldThrowWhenReproducaoNotFoundOnUpdate() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reproducaoService.update(1L, createReproducaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reproducao não encontrado");
    }

    @Test
    void shouldDeleteReproducao() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.of(reproducao));

        reproducaoService.delete(1L);

        verify(reproducaoRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenReproducaoNotFoundOnDelete() {
        when(reproducaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reproducaoService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reproducao não encontrado");
    }
}
