package br.com.genovi.dtos.medicamento;

import br.com.genovi.dtos.doencas.DoencaDTO;

import java.util.List;

public record MedicamentoDTO(
        Long id,
        String nome,
        String fabricante,
        List<DoencaDTO> doencas,
        Boolean doseUnica,
        Integer quantidadeDoses,
        boolean isVacina) {
}
