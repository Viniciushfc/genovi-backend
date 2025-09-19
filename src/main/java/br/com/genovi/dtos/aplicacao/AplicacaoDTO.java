package br.com.genovi.dtos.aplicacao;

import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.time.LocalDateTime;

public record AplicacaoDTO(LocalDateTime dataAplicacao,
                           OvinoDTO ovino,
                           MedicamentoDTO medicamento,
                           LocalDateTime dataProximaDose) {
}
