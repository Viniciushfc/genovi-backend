package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.TypeReproducao;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;

import java.time.LocalDateTime;

public record ReproducaoDTO(
        Long id,
        LocalDateTime dataReproducao,
        OvinoResumoDTO carneiro,
        OvinoResumoDTO ovelha,
        TypeReproducao typeReproducao,
        String observacoes) {
}
