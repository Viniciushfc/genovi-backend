package br.com.genovi.dtos.amamentacao;

import java.time.LocalDateTime;

public record CreateAmamentacaoDTO(Long ovelhaMaeId,
                                   Long cordeiroMamandoId,
                                   LocalDateTime dataInicio,
                                   LocalDateTime dataFim,
                                   String observacoes) {
}
