package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.AmamentacaoServiceImpl;
import br.com.genovi.domain.models.Amamentacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;
import br.com.genovi.infrastructure.mapper.AmamentacaoMapper;
import br.com.genovi.infrastructure.repositories.AmamentacaoRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AmamentacaoServiceTest {

    @Mock
    private AmamentacaoRepository amamentacaoRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private AmamentacaoMapper amamentacaoMapper;

    private Ovino ovelhaMae;

    private Ovino cordeiro;

    @Mock
    private Amamentacao amamentacao;

    private AmamentacaoDTO amamentacaoDTO;

    @Mock
    private CreateAmamentacaoDTO createDto;

    @InjectMocks
    private AmamentacaoServiceImpl amamentacaoService;

    @BeforeEach
    void setUp() {
        ovelhaMae = new Ovino();
        ovelhaMae.setId(1L);

        cordeiro = new Ovino();
        cordeiro.setId(2L);

        LocalDateTime dataTeste = LocalDateTime.of(2024, 1, 1, 14, 30);

        amamentacao = new Amamentacao();
        OvinoResumoDTO maeResumo = new OvinoResumoDTO(1L, 101L, "Mae", "fbb1");
        OvinoResumoDTO cordeiroResumo = new OvinoResumoDTO(2L, 102L, "Cordeiro", "fbb2");
        amamentacaoDTO = new AmamentacaoDTO(maeResumo, cordeiroResumo, dataTeste, dataTeste.plusHours(10), "Observações testes");

        createDto = new CreateAmamentacaoDTO(1L, 2L, dataTeste, dataTeste.plusHours(10), "Observações testes");
    }

    @Test
    void findAllDeveRetornarListaDeDTOs() {
        when(amamentacaoRepository.findAll()).thenReturn(List.of(amamentacao));
        when(amamentacaoMapper.toDTO(amamentacao)).thenReturn(amamentacaoDTO);

        List<AmamentacaoDTO> result = amamentacaoService.findAll();

        assertEquals(1, result.size());
        verify(amamentacaoRepository).findAll();
    }

    @Test
    void findByIdDeveRetornarDTOQuandoEncontrado() {
        when(amamentacaoRepository.findById(1L)).thenReturn(Optional.of(amamentacao));
        when(amamentacaoMapper.toDTO(amamentacao)).thenReturn(amamentacaoDTO);

        AmamentacaoDTO result = amamentacaoService.findById(1L);

        assertNotNull(result);
        verify(amamentacaoRepository).findById(1L);
    }

    @Test
    void findByIdDeveLancarExcecaoQuandoNaoEncontrado() {
        when(amamentacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> amamentacaoService.findById(1L));
    }

    @Test
    void saveDeveSalvarEMapearDTO() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelhaMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(cordeiro));
        when(amamentacaoMapper.toEntity(createDto, ovelhaMae, cordeiro)).thenReturn(amamentacao);
        when(amamentacaoMapper.toDTO(amamentacao)).thenReturn(amamentacaoDTO);

        AmamentacaoDTO result = amamentacaoService.save(createDto);

        assertNotNull(result);
        verify(amamentacaoRepository).save(amamentacao);
    }

    @Test
    void updateDeveAtualizarQuandoEncontrado() {
        when(amamentacaoRepository.findById(1L)).thenReturn(Optional.of(amamentacao));
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovelhaMae));
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(cordeiro));
        when(amamentacaoRepository.save(any(Amamentacao.class))).thenReturn(amamentacao);
        when(amamentacaoMapper.toDTO(any(Amamentacao.class))).thenReturn(amamentacaoDTO);

        AmamentacaoDTO result = amamentacaoService.update(1L, createDto);

        assertNotNull(result);
        verify(amamentacaoRepository).save(any(Amamentacao.class));
    }

    @Test
    void deleteDeveRemoverQuandoEncontrado() {
        when(amamentacaoRepository.findById(1L)).thenReturn(Optional.of(amamentacao));

        amamentacaoService.delete(1L);

        verify(amamentacaoRepository).deleteById(1L);
    }

    @Test
    void deleteDeveLancarExcecao_QuandoNaoEncontrado() {
        when(amamentacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> amamentacaoService.delete(1L));
    }
}
