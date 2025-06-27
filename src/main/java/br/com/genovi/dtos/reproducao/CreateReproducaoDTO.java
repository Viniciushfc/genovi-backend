package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.TypeReproducao;

import java.time.LocalDateTime;

public record CreateReproducaoDTO(LocalDateTime dataReproducao,
                                  Long carneiroId,
                                  Long ovelhaId,
                                  TypeReproducao typeReproducao,
                                  String observacoes) {
}
