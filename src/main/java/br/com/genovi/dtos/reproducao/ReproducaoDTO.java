package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.TypeReproducao;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;

import java.time.LocalDateTime;

public record ReproducaoDTO(
        Long id,
        LocalDateTime dataReproducao,
        OvinoResumeDTO carneiro,
        OvinoResumeDTO ovelha,
        TypeReproducao typeReproducao,
        String observacoes) {
}
