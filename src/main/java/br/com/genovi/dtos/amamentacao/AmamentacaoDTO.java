package br.com.genovi.dtos.amamentacao;

import br.com.genovi.domain.models.Ovino;

import java.time.LocalDateTime;

public record AmamentacaoDTO(Ovino ovelhaMae,
                             Ovino cordeiroMamando,
                             LocalDateTime dataInicio,
                             LocalDateTime dataFim,
                             String observacoes) {
}
