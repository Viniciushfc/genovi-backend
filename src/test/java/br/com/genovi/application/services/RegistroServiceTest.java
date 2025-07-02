package br.com.genovi.application;

import br.com.genovi.application.services.RegistroService;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.RegistroDTO;
import br.com.genovi.dtos.relatorios.RegistroRecord;
import br.com.genovi.infrastructure.mappers.OvinoMapper;
import br.com.genovi.infrastructure.mappers.RegistroMapper;
import br.com.genovi.infrastructure.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceTest {
    
    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private PartoRepository partoRepository;

    @Mock
    private CicloCioRepository cicloCioRepository;

    @Mock
    private AmamentacaoRepository amamentacaoRepository;

    @Mock
    private ReproducaoRepository reproducaoRepository;

    @Mock
    private AplicacaoRepository aplicacaoRepository;

    @Mock
    private OcorrenciaDoencaRepository ocorrenciaDoencaRepository;

    @Mock
    private RegistroMapper registroMapper;

    @Mock
    private OvinoMapper ovinoMapper;

    @Mock
    private Ovino ovino;

    @Mock
    private RegistroDTO registroDTO;

    @Mock
    private RegistroRecord registroRecord;

    @InjectMocks
    private RegistroService registroService;

    @BeforeEach
    void setUp() {
        ovino = new Ovino();
        ovino.setId(1L);

        registroDTO = new RegistroDTO(ovinoMapper.toDTO(ovino), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        registroRecord = new RegistroRecord(ovino, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    @Test
    void deveGerarRegistroQuandoOvinoExiste() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(partoRepository.findByOvelhaMaeId(1L)).thenReturn(Collections.emptyList());
        when(cicloCioRepository.findByOvelhaId(1L)).thenReturn(Collections.emptyList());
        when(amamentacaoRepository.findByOvelhaMaeIdOrCordeiroMamandoId(1L, 1L)).thenReturn(Collections.emptyList());
        when(reproducaoRepository.findByCarneiroPaiIdOrOvelhaMaeId(1L, 1L)).thenReturn(Collections.emptyList());
        when(aplicacaoRepository.findByOvinoId(1L)).thenReturn(Collections.emptyList());
        when(ocorrenciaDoencaRepository.findByOvinoId(1L)).thenReturn(Collections.emptyList());

        when(registroMapper.toEntity(
                ovino,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        )).thenReturn(registroRecord);

        when(registroMapper.toDTO(registroRecord)).thenReturn(registroDTO);

        RegistroDTO resultado = registroService.gerarRegistroPorOvinoById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo(registroDTO);
    }

    @Test
    void deveLancarExcecaoQuandoOvinoNaoForEncontrado() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registroService.gerarRegistroPorOvinoById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ovino não encontrado");
    }
}
