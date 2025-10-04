package br.com.genovi.dtos.medicamento;

import java.util.List;

public record CreateMedicamentoDTO(String nome,
                                   String fabricante,
                                   List<Long> doencasIds,
                                   Integer intervaloDoses,
                                   Integer quantidadeDoses,
                                   boolean isVacina) {
}
