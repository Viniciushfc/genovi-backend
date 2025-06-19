package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.enums.TypeReproducao;
import br.com.genovi.application.domain.models.Ovino;

import java.time.LocalDateTime;

public record ReproducaoDTO(LocalDateTime dataReproducao,
                            Ovino carneiroId,
                            Ovino ovelhaId,
                            TypeReproducao typeReproducao,
                            String observacoes) {
}
