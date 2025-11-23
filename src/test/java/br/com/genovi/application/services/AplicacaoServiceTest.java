package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.AplicacaoServiceImpl;
import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import br.com.genovi.application.mapper.AplicacaoMapper;
import br.com.genovi.application.mapper.MedicamentoMapper;
import br.com.genovi.application.mapper.OvinoMapper;
import br.com.genovi.application.mapper.UsuarioMapper;
import br.com.genovi.application.services.impl.RegistroServiceImpl;
import br.com.genovi.infrastructure.repository.AplicacaoRepository;
import br.com.genovi.infrastructure.repository.MedicamentoRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AplicacaoServiceTest {

    @Mock
    private AplicacaoRepository aplicacaoRepository;

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private AplicacaoMapper aplicacaoMapper;

    @Mock
    private OvinoMapper ovinoMapper;

    @Mock
    private MedicamentoMapper medicamentoMapper;

    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private RegistroServiceImpl registroService;

    @InjectMocks
    private AplicacaoServiceImpl aplicacaoService;

    private Ovino ovino;
    private Medicamento medicamento;
    private Usuario usuario;
    private Aplicacao aplicacao;
    private AplicacaoDTO aplicacaoDTO;
    private CreateAplicacaoDTO createDto;

    @BeforeEach
    void setUp() {
        ovino = new Ovino();
        ovino.setId(1L);

        medicamento = new Medicamento();
        medicamento.setId(2L);

        usuario = new Usuario();
        usuario.setId(3L);

        aplicacao = new Aplicacao();
        aplicacao.setId(1L); // essencial para o teste delete

        aplicacaoDTO = new AplicacaoDTO(
                1L,
                LocalDateTime.of(2024, 1, 1, 10, 0),
                ovinoMapper.toDTO(ovino),
                medicamentoMapper.toDTO(medicamento),
                LocalDateTime.of(2024, 1, 1, 10, 0)
        );

        createDto = new CreateAplicacaoDTO(
                LocalDateTime.of(2024, 1, 1, 10, 0),
                1L, // ovinoId
                2L, // medicamentoId
                LocalDateTime.of(2024, 1, 1, 10, 0),
                3L // usuarioId
        );
    }

    @Test
    void findAllDeveRetornarListaDeDTOs() {
        when(aplicacaoRepository.findAll()).thenReturn(List.of(aplicacao));
        when(aplicacaoMapper.toDTO(aplicacao)).thenReturn(aplicacaoDTO);

        List<AplicacaoDTO> result = aplicacaoService.findAll();

        assertEquals(1, result.size());
        verify(aplicacaoRepository).findAll();
        verify(aplicacaoMapper).toDTO(aplicacao);
    }

    @Test
    void findByIdDeveRetornarDTOQuandoEncontrado() {
        when(aplicacaoRepository.findById(1L)).thenReturn(Optional.of(aplicacao));
        when(aplicacaoMapper.toDTO(aplicacao)).thenReturn(aplicacaoDTO);

        AplicacaoDTO result = aplicacaoService.findById(1L);

        assertNotNull(result);
        verify(aplicacaoRepository).findById(1L);
    }

    @Test
    void findByIdDeveLancarExcecaoQuandoNaoEncontrado() {
        when(aplicacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aplicacaoService.findById(1L));
    }

    @Test
    void saveDeveSalvarEMapearDTO() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(medicamentoRepository.findById(2L)).thenReturn(Optional.of(medicamento));
        when(aplicacaoRepository.save(any(Aplicacao.class))).thenReturn(aplicacao);
        when(aplicacaoMapper.toDTO(any(Aplicacao.class))).thenReturn(aplicacaoDTO);

        AplicacaoDTO result = aplicacaoService.save(createDto);

        assertNotNull(result);
        verify(aplicacaoRepository).save(any(Aplicacao.class));
    }

    @Test
    void saveDeveLancarExcecaoSeOvinoNaoEncontrado() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aplicacaoService.save(createDto));
    }

    @Test
    void saveDeveLancarExcecaoSeMedicamentoNaoEncontrado() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));
        when(medicamentoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aplicacaoService.save(createDto));
    }

    @Test
    void deleteDeveRemoverQuandoEncontrado() {
        when(aplicacaoRepository.findById(1L)).thenReturn(Optional.of(aplicacao));

        aplicacaoService.delete(1L);

        verify(aplicacaoRepository).deleteById(1L);
    }

    @Test
    void deleteDeveLancarExcecaoQuandoNaoEncontrado() {
        when(aplicacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aplicacaoService.delete(1L));
    }
}
