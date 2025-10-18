package br.com.genovi.dtos.amamentacao;

import br.com.genovi.dtos.ovino.OvinoResumoDTO;

import java.time.LocalDateTime;

public record AmamentacaoDTO(OvinoResumoDTO ovelhaMae,
                             OvinoResumoDTO cordeiroMamando,
                             LocalDateTime dataInicio,
                             LocalDateTime dataFim,
                             String observacoes) {
}
