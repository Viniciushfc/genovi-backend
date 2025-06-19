package br.com.genovi.application.dtos;

import java.util.List;

public record CreateMedicamentoDTO (String nome,
                                    String fabricante,
                                    List<Long> doencasIds,
                                    Boolean doseUnica,
                                    String descricao){
}
