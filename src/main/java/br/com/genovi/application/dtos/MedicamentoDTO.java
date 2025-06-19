package br.com.genovi.application.dtos;

import java.util.List;

public record MedicamentoDTO(String nome,
                             String fabricante,
                             List<DoencaDTO> doencas,
                             Boolean doseUnica,
                             String descricao) {
}
