package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Ovino;

import java.time.LocalDateTime;

public record AmamantacaoDTO(Ovino ovelhaMae,
                             Ovino cordeiroMamando,
                             LocalDateTime dataInicio,
                             LocalDateTime dataFim,
                             String observacoes) {
}
