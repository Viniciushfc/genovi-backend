package br.com.genovi.dtos.gestacao;

import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.time.LocalDateTime;

public record GestacaoDTO(
        Long id,
        OvinoResumeDTO ovelhaMae,
        OvinoResumeDTO ovelhaPai,
        ReproducaoDTO reproducao,
        LocalDateTime dataGestacao) {
}
