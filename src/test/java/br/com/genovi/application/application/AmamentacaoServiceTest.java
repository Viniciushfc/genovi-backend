package br.com.genovi.application.application;

import br.com.genovi.application.application.services.AmamentacaoService;
import br.com.genovi.application.domain.models.Amamentacao;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.AmamentacaoDTO;
import br.com.genovi.application.dtos.CreateAmamentacaoDTO;
import br.com.genovi.application.infrastructure.mappers.AmamentacaoMapper;
import br.com.genovi.application.infrastructure.repositories.AmamentacaoRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmamentacaoServiceTest {

    @Mock
    private AmamentacaoRepository amamentacaoRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private AmamentacaoMapper amamentacaoMapper;

    @InjectMocks
    private AmamentacaoService amamentacaoService;

    private Ovino ovelhaMae;
    private Ovino cordeiro;
    private Amamentacao amamentacao;
    private AmamentacaoDTO amamentacaoDTO;
    private CreateAmamentacaoDTO createDto;

    @BeforeEach
    void setUp() {
        ovelhaMae = new Ovino();
        ovelhaMae.setId(1L);

        cordeiro = new Ovino();
        cordeiro.setId(2L);

        LocalDateTime dataTeste = LocalDateTime.of(2024, 1, 1, 14, 30);

        amamentacao = new Amamentacao();
        amamentacaoDTO = new AmamentacaoDTO(ovelhaMae, cordeiro,  dataTeste, dataTeste.plusHours(10), "Observações testes");

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
        when(amamentacaoMapper.toDTO(amamentacao)).thenReturn(amamentacaoDTO);

        AmamentacaoDTO result = amamentacaoService.update(1L, createDto);

        assertNotNull(result);
        verify(amamentacaoRepository).save(amamentacao);
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
