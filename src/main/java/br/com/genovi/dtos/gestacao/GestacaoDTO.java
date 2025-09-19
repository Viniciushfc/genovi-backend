package br.com.genovi.dtos.gestacao;

import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

public record GestacaoDTO(
        Long id,
        OvinoDTO ovelhaMae,
        OvinoDTO ovelhaPai,
        ReproducaoDTO reproducao) {
}
