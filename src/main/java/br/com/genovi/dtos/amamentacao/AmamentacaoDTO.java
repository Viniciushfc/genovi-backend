package br.com.genovi.dtos.amamentacao;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.time.LocalDateTime;

public record AmamentacaoDTO(OvinoDTO ovelhaMae,
                             OvinoDTO cordeiroMamando,
                             LocalDateTime dataInicio,
                             LocalDateTime dataFim,
                             String observacoes) {
}
