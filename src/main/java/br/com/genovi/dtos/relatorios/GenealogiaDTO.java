package br.com.genovi.dtos.relatorios;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ovino.OvinoDTO;

public record GenealogiaDTO(
        OvinoDTO ovino,
        GenealogiaDTO carneiroPai,
        GenealogiaDTO ovelhaMae
) {
}
