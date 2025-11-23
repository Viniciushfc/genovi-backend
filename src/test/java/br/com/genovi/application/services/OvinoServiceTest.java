package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.OvinoServiceImpl;
import br.com.genovi.domain.enums.EnumStatus;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.application.mapper.OvinoMapper;
import br.com.genovi.infrastructure.repository.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OvinoServiceTest {

    @Mock
    private OvinoRepository ovinoRepository;

    @Mock
    private AscendenciaRepository ascendenciaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private PartoRepository partoRepository;

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private PesagemRepository pesagemRepository;

    @Mock
    private OvinoMapper ovinoMapper;

    @InjectMocks
    private OvinoServiceImpl ovinoService;

    private Ovino ovino;
    private Compra compra;
    private Parto parto;
    private Pesagem pesagem;
    private CreateOvinoDTO createOvinoDTO;

    @BeforeEach
    void setUp() {
        ovino = new Ovino();
        ovino.setId(1L);
        ovino.setNome("Ovino Teste");

        compra = new Compra();
        compra.setId(1L);

        parto = new Parto();
        parto.setId(1L);

        pesagem = new Pesagem();
        pesagem.setId(1L);

        createOvinoDTO = new CreateOvinoDTO(
                123L,
                "Ovino Teste",
                null, // raca
                null, // fbb
                LocalDateTime.now(),
                LocalDateTime.now(),
                null, // grau pureza
                null, // sexo
                1L, // maeId
                2L, // paiId
                EnumStatus.ATIVO,
                null, // foto
                1L, // compra
                1L, // parto
                List.of(1L) // pesos
        );
    }

    @Test
    void shouldSaveNewOvino() {
        // Mocks para dependências
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino)); // mae
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(ovino)); // pai
        when(compraRepository.findById(1L)).thenReturn(Optional.of(compra));
        when(partoRepository.findById(1L)).thenReturn(Optional.of(parto));
        when(pesagemRepository.findAllById(List.of(1L))).thenReturn(List.of(pesagem));
        when(ovinoRepository.save(any(Ovino.class))).thenReturn(ovino);
        when(ovinoMapper.toDTO(any(Ovino.class))).thenReturn(mock(OvinoDTO.class));

        OvinoDTO result = ovinoService.save(createOvinoDTO);

        assertNotNull(result);
        verify(ovinoRepository).save(any(Ovino.class));
    }

    @Test
    void shouldUpdateOvino() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino)); // entidade a atualizar e mae
        when(ovinoRepository.findById(2L)).thenReturn(Optional.of(new Ovino())); // pai
        when(compraRepository.findById(1L)).thenReturn(Optional.of(compra));
        when(partoRepository.findById(1L)).thenReturn(Optional.of(parto));
        when(pesagemRepository.findAllById(List.of(1L))).thenReturn(List.of(pesagem));
        when(ovinoRepository.save(ovino)).thenReturn(ovino);
        when(ovinoMapper.toDTO(ovino)).thenReturn(mock(OvinoDTO.class));

        OvinoDTO updated = ovinoService.update(1L, createOvinoDTO);

        assertNotNull(updated);
        verify(ovinoRepository).save(ovino);
    }

    @Test
    void shouldThrowWhenOvinoNotFoundOnUpdate() {
        when(ovinoRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ovinoService.update(1L, createOvinoDTO));

        assertEquals("Ovino não encontrado", exception.getMessage());
    }

    @Test
    void shouldDisableOvino() {
        when(ovinoRepository.findById(1L)).thenReturn(Optional.of(ovino));

        ovinoService.disable(1L);

        assertEquals(EnumStatus.DESATIVADO, ovino.getStatus());
        verify(ovinoRepository).save(ovino);
    }

    @Test
    void shouldThrowWhenOvinoNotFoundOnDisable() {
        when(ovinoRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ovinoService.disable(1L));

        assertEquals("Ovino não encontrado", exception.getMessage());
    }
}
