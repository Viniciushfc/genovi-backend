package br.com.genovi.dtos.gestacao;

import br.com.genovi.dtos.ovino.OvinoResumoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.time.LocalDateTime;

public record GestacaoDTO(
        Long id,
        OvinoResumoDTO ovelhaMae,
        OvinoResumoDTO ovelhaPai,
        ReproducaoDTO reproducao,
        LocalDateTime dataGestacao) {
}
