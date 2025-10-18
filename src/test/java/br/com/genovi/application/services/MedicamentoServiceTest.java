package br.com.genovi.application.services;

import br.com.genovi.application.services.impl.MedicamentoServiceImpl;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.infrastructure.mapper.MedicamentoMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
import br.com.genovi.infrastructure.repositories.MedicamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private DoencaRepository doencaRepository;

    @Mock
    private MedicamentoMapper medicamentoMapper;

    @Mock
    private Medicamento medicamento;

    @Mock
    private Doenca doenca;

    @Mock
    private CreateMedicamentoDTO createMedicamentoDTO;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    @BeforeEach
    void setUp() {
        doenca = new Doenca();
        doenca.setId(1L);

        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Remédio Teste");

        createMedicamentoDTO = new CreateMedicamentoDTO(
                "Remédio Teste",
                "Fabricante Teste",
                List.of(1L),
                1,
                1,
                true
        );
    }

    @Test
    void testFindAll() {
        MedicamentoDTO medicamentoDTO = new MedicamentoDTO(
                1L, "Remédio Teste", "Fabricante Teste", 1, List.of(new DoencaDTO(1L, "Nome Doença", "Doença X")), 1, true);

        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));
        when(medicamentoMapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        List<MedicamentoDTO> result = medicamentoService.findAll();

        assertEquals(1, result.size());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        MedicamentoDTO medicamentoDTO = new MedicamentoDTO(
                1L, "Remédio Teste", "Fabricante Teste", 1, List.of(new DoencaDTO(1L, "Nome Doença", "Doença X")), 1, true);

        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(medicamentoMapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        MedicamentoDTO result = medicamentoService.findById(1L);

        assertNotNull(result);
        verify(medicamentoRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        MedicamentoDTO medicamentoDTO = new MedicamentoDTO(
                1L, "Remédio Teste", "Fabricante Teste", 1, List.of(new DoencaDTO(1L, "Nome Doença", "Doença X")), 1, true);

        when(doencaRepository.findAllById(List.of(1L))).thenReturn(List.of(doenca));
        when(medicamentoMapper.toEntity(createMedicamentoDTO, List.of(doenca))).thenReturn(medicamento);
        when(medicamentoMapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        MedicamentoDTO result = medicamentoService.save(createMedicamentoDTO);

        assertNotNull(result);
        assertEquals("Remédio Teste", result.nome());
        verify(medicamentoRepository).save(medicamento);
    }

    @Test
    void testSaveWithInvalidDoencas() {
        when(doencaRepository.findAllById(List.of(1L))).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                medicamentoService.save(createMedicamentoDTO)
        );

        assertEquals("Uma ou mais Doenças não foram encontradas.", exception.getMessage());
    }

    @Test
    void testUpdate() {
        MedicamentoDTO medicamentoDTO = new MedicamentoDTO(
                1L, "Remédio Teste", "Fabricante Teste", 1, List.of(new DoencaDTO(1L, "Nome Doença", "Doença X")), 1, true);


        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(doencaRepository.findAllById(List.of(1L))).thenReturn(List.of(doenca));
        when(medicamentoMapper.toEntity(createMedicamentoDTO, List.of(doenca))).thenReturn(medicamento);
        when(medicamentoMapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        MedicamentoDTO result = medicamentoService.update(1L, createMedicamentoDTO);

        assertNotNull(result);
        verify(medicamentoMapper).toEntity(createMedicamentoDTO, List.of(doenca));
        verify(medicamentoRepository).save(medicamento);
    }

    @Test
    void testUpdateWithInvalidDoencas() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(doencaRepository.findAllById(List.of(1L))).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                medicamentoService.update(1L, createMedicamentoDTO)
        );

        assertEquals("Uma ou mais Doenças não foram encontradas.", exception.getMessage());
    }

    @Test
    void testDelete() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));

        medicamentoService.delete(1L);

        verify(medicamentoRepository).deleteById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(medicamentoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                medicamentoService.findById(2L)
        );

        assertTrue(exception.getMessage().contains("Medicamento não encontrado"));
    }

    @Test
    void testDeleteNotFound() {
        when(medicamentoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                medicamentoService.delete(2L)
        );

        assertTrue(exception.getMessage().contains("Medicamento não encontrado"));
    }
}
