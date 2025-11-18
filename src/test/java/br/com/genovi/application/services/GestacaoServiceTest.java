package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.GestacaoServiceImpl;
import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.application.mapper.GestacaoMapper;
import br.com.genovi.infrastructure.repository.GestacaoRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.ReproducaoRepository;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestacaoServiceTest {

    @Mock
    private GestacaoRepository gestacaoRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private ReproducaoRepository reproducaoRepository;

    @Mock
    private GestacaoMapper gestacaoMapper;

    @InjectMocks
    private GestacaoServiceImpl gestacaoService;

    private Ovino ovinoMae;
    private Ovino ovinoPai;
    private Reproducao reproducao;
    private Gestacao gestacao;
    private CreateGestacaoDTO createGestacaoDTO;
    private GestacaoDTO gestacaoDTO;

    @BeforeEach
    void setup() {
        ovinoMae = new Ovino();
        ovinoMae.setId(1L);

        ovinoPai = new Ovino();
        ovinoPai.setId(2L);

        reproducao = new Reproducao();
        reproducao.setId(3L);

        gestacao = new Gestacao();
        gestacao.setId(10L);

        createGestacaoDTO = new CreateGestacaoDTO(1L, 2L, 3L, LocalDateTime.now());

        OvinoResumeDTO maeResumo = new OvinoResumeDTO(1L, 101L, "Mae", "fbb1");
        OvinoResumeDTO paiResumo = new OvinoResumeDTO(2L, 102L, "Pai", "fbb2");
        ReproducaoDTO reproducaoDTO = new ReproducaoDTO(3L, LocalDateTime.now(), paiResumo, maeResumo, null);
        gestacaoDTO = new GestacaoDTO(10L, maeResumo, paiResumo, reproducaoDTO, LocalDateTime.now());
    }

    @Test
    void deveSalvarGestacaoComSucesso() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(gestacaoMapper.toEntity(createGestacaoDTO, ovinoMae, ovinoPai, reproducao)).thenReturn(gestacao);
        when(gestacaoRepository.save(gestacao)).thenReturn(gestacao);
        when(gestacaoMapper.toDTO(gestacao)).thenReturn(gestacaoDTO);

        GestacaoDTO result = gestacaoService.save(createGestacaoDTO);

        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verify(reproducaoRepository).findById(3L);
        verify(gestacaoMapper).toEntity(createGestacaoDTO, ovinoMae, ovinoPai, reproducao);
        verify(gestacaoRepository).save(gestacao);
        verify(gestacaoMapper).toDTO(gestacao);
        assertThat(result).isEqualTo(gestacaoDTO);
    }

    @Test
    void deveLancarExcecaoQuandoOvelhaMaeNaoForEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.save(createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrada");

        verify(ovinoRepository).findById(1L);
        verifyNoMoreInteractions(ovinoRepository);
        verifyNoInteractions(reproducaoRepository, gestacaoMapper, gestacaoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoOvelhaPaiNaoForEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.save(createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrada");

        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verifyNoInteractions(reproducaoRepository, gestacaoMapper, gestacaoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoReproducaoNaoEncontrada() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.save(createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reprodução não encontrada");

        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verify(reproducaoRepository).findById(3L);
        verifyNoInteractions(gestacaoMapper, gestacaoRepository);
    }

    @Test
    void deveAtualizarGestacaoComSucesso() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(gestacaoRepository.save(gestacao)).thenReturn(gestacao);
        when(gestacaoMapper.toDTO(gestacao)).thenReturn(gestacaoDTO);

        GestacaoDTO result = gestacaoService.update(10L, createGestacaoDTO);

        verify(gestacaoRepository).findById(10L);
        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verify(reproducaoRepository).findById(3L);
        verify(gestacaoRepository).save(gestacao);
        verify(gestacaoMapper).toDTO(gestacao);
        assertThat(result).isEqualTo(gestacaoDTO);
    }

    @Test
    void deveLancarExcecaoAoAtualizarGestacaoNaoExistente() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.update(10L, createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Gestação não encontrada");

        verify(gestacaoRepository).findById(10L);
        verifyNoInteractions(ovinoRepository, reproducaoRepository, gestacaoMapper);
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuandoOvelhaMaeNaoExistir() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.update(10L, createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrada");

        verify(gestacaoRepository).findById(10L);
        verify(ovinoRepository).findById(1L);
        verifyNoMoreInteractions(ovinoRepository);
        verifyNoInteractions(reproducaoRepository, gestacaoMapper);
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuandoOvelhaPaiNaoExistir() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.update(10L, createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrada");

        verify(gestacaoRepository).findById(10L);
        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verifyNoInteractions(reproducaoRepository, gestacaoMapper);
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuandoReproducaoNaoExistir() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.update(10L, createGestacaoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Reprodução não encontrada");

        verify(gestacaoRepository).findById(10L);
        verify(ovinoRepository).findById(1L);
        verify(ovinoRepository).findById(2L);
        verify(reproducaoRepository).findById(3L);
        verifyNoInteractions(gestacaoMapper);
    }

    @Test
    void deveDeletarGestacaoComSucesso() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));

        gestacaoService.delete(10L);

        verify(gestacaoRepository).findById(10L);
        verify(gestacaoRepository).deleteById(10L);
    }

    @Test
    void deveLancarExcecaoAoDeletarGestacaoNaoExistente() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.delete(10L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Gestação não encontrada");

        verify(gestacaoRepository).findById(10L);
        verify(gestacaoRepository, never()).deleteById(any());
    }

    @Test
    void deveRetornarGestacaoPorId() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(gestacaoMapper.toDTO(gestacao)).thenReturn(gestacaoDTO);

        GestacaoDTO result = gestacaoService.findById(10L);

        verify(gestacaoRepository).findById(10L);
        verify(gestacaoMapper).toDTO(gestacao);
        assertThat(result).isEqualTo(gestacaoDTO);
    }

    @Test
    void deveLancarExcecaoQuandoGestacaoNaoEncontradaPorId() {
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gestacaoService.findById(10L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Gestação não encontrada");

        verify(gestacaoRepository).findById(10L);
        verifyNoInteractions(gestacaoMapper);
    }

    @Test
    void deveRetornarTodasGestacoes() {
        Gestacao gestacao2 = new Gestacao();
        gestacao2.setId(11L);
        List<Gestacao> gestacoes = Arrays.asList(gestacao, gestacao2);

        ReproducaoDTO reproducaoDTO2 = new ReproducaoDTO(4L, LocalDateTime.now(), null, null, null);
        GestacaoDTO gestacaoDTO2 = new GestacaoDTO(11L, null, null, reproducaoDTO2, LocalDateTime.now());

        when(gestacaoRepository.findAll()).thenReturn(gestacoes);
        when(gestacaoMapper.toDTO(gestacao)).thenReturn(gestacaoDTO);
        when(gestacaoMapper.toDTO(gestacao2)).thenReturn(gestacaoDTO2);

        List<GestacaoDTO> result = gestacaoService.findAll();

        verify(gestacaoRepository).findAll();
        verify(gestacaoMapper).toDTO(gestacao);
        verify(gestacaoMapper).toDTO(gestacao2);
        assertThat(result).hasSize(2).containsExactly(gestacaoDTO, gestacaoDTO2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremGestacoes() {
        when(gestacaoRepository.findAll()).thenReturn(Collections.emptyList());

        List<GestacaoDTO> result = gestacaoService.findAll();

        verify(gestacaoRepository).findAll();
        verifyNoInteractions(gestacaoMapper);
        assertThat(result).isEmpty();
    }

    @Test
    void deveVerificarBugNoBuscaPaiSave() {
        // Este teste FALHARÁ com o código atual devido ao bug
        // O código busca dto.ovelhaMaeId() duas vezes ao invés de dto.ovelhaPaiId()
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(gestacaoMapper.toDTO(any())).thenReturn(gestacaoDTO);
        when(gestacaoRepository.save(any())).thenReturn(gestacao);

        gestacaoService.save(createGestacaoDTO);

        // Verifica que ambos os IDs foram buscados corretamente
        verify(ovinoRepository).findById(1L); // ID da mãe
        verify(ovinoRepository).findById(2L); // ID do pai - FALHARÁ com o bug
    }

    @Test
    void deveVerificarBugNoBuscaPaiUpdate() {
        // Este teste FALHARÁ com o código atual devido ao bug
        when(gestacaoRepository.findById(10L)).thenReturn(Optional.of(gestacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovinoMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovinoPai));
        when(reproducaoRepository.findById(3L)).thenReturn(Optional.of(reproducao));
        when(gestacaoMapper.toDTO(any())).thenReturn(gestacaoDTO);
        when(gestacaoRepository.save(any())).thenReturn(gestacao);

        gestacaoService.update(10L, createGestacaoDTO);

        // Verifica que ambos os IDs foram buscados corretamente
        verify(ovinoRepository).findById(1L); // ID da mãe
        verify(ovinoRepository).findById(2L); // ID do pai - FALHARÁ com o bug
    }
}