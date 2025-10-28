package br.com.genovi.dtos.ascendencia;

import br.com.genovi.dtos.ovino.OvinoDTO;

public record AscendenciaDTO(Long id,
                             OvinoDTO ovinoPai,
                             OvinoDTO ovinoMae) {
}
