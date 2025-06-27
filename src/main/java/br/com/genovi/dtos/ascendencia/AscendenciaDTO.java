package br.com.genovi.dtos.ascendencia;

import br.com.genovi.domain.models.Ovino;

public record AscendenciaDTO(Ovino ovinoPai,
                             Ovino ovinoMae) {
}
