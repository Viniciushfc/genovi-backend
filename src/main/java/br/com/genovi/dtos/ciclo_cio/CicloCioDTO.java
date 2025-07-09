package br.com.genovi.dtos.ciclo_cio;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.time.LocalDateTime;

public record CicloCioDTO(OvinoDTO ovelha,
                          LocalDateTime dataInicio,
                          LocalDateTime dataFim,
                          String observacoes) {
}
