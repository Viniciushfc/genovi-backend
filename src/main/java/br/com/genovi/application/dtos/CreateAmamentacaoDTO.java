package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Ovino;

import java.time.LocalDateTime;

public record CreateAmamentacaoDTO(Long ovelhaMaeId,
                                   Long cordeiroMamandoId,
                                   LocalDateTime dataInicio,
                                   LocalDateTime dataFim,
                                   String observacoes) {
}
