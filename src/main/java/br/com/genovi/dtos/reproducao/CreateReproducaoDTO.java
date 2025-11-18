package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.EnumReproducao;

import java.time.LocalDateTime;

public record CreateReproducaoDTO(LocalDateTime dataReproducao,
                                  Long carneiroId,
                                  Long ovelhaId,
                                  EnumReproducao enumReproducao) {
}
