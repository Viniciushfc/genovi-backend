package br.com.genovi.dtos.ciclo_cio;

import br.com.genovi.domain.models.Ovino;

import java.time.LocalDateTime;

public record CicloCioDTO(Ovino ovelha,
                          LocalDateTime dataInicio,
                          LocalDateTime dataFim,
                          String observacoes) {
}
