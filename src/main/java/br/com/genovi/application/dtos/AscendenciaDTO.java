package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Ovino;

public record AscendenciaDTO(Ovino ovinoPai,
                             Ovino ovinoMae) {
}
