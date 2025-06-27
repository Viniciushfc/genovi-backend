package br.com.genovi.dtos.ciclo_cio;

import java.time.LocalDateTime;

public record CreateCicloCioDTO(Long ovelhaId,
                                LocalDateTime dataInicio,
                                LocalDateTime dataFim,
                                String observacoes) {
}
