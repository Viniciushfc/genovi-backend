package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.enums.TypeReproducao;

import java.time.LocalDateTime;

public record CreateReproducaoDTO(LocalDateTime dataReproducao,
                                  Long carneiroId,
                                  Long ovelhaId,
                                  TypeReproducao typeReproducao,
                                  String observacoes) {
}
