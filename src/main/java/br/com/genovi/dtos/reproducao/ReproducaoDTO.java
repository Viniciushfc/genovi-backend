package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.TypeReproducao;
import br.com.genovi.domain.models.Ovino;

import java.time.LocalDateTime;

public record ReproducaoDTO(LocalDateTime dataReproducao,
                            Ovino carneiroId,
                            Ovino ovelhaId,
                            TypeReproducao typeReproducao,
                            String observacoes) {
}
