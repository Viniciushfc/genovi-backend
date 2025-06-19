package br.com.genovi.application.dtos;

import java.time.LocalDateTime;

public record CreateCicloCioDTO(Long ovelhaId,
                                LocalDateTime dataInicio,
                                LocalDateTime dataFim,
                                String observacoes) {
}
