package br.com.genovi.dtos.ascendencia;

import br.com.genovi.dtos.ovino.OvinoDTO;

public record AscendenciaDTO(OvinoDTO ovinoPai,
                             OvinoDTO ovinoMae) {
}
