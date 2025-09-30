package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.TypeReproducao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.time.LocalDateTime;

public record ReproducaoDTO(
        Long id,
        LocalDateTime dataReproducao,
        OvinoDTO carneiroId,
        OvinoDTO ovelhaId,
        TypeReproducao typeReproducao,
        String observacoes) {
}
