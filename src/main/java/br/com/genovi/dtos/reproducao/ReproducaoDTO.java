package br.com.genovi.dtos.reproducao;

import br.com.genovi.domain.enums.EnumReproducao;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;

import java.time.LocalDateTime;

public record ReproducaoDTO(
        Long id,
        LocalDateTime dataReproducao,
        OvinoResumeDTO carneiro,
        OvinoResumeDTO ovelha,
        EnumReproducao enumReproducao,
        String observacoes) {
}
