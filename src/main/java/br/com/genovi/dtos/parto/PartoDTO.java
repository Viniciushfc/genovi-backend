package br.com.genovi.dtos.parto;

import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.time.LocalDateTime;

public record PartoDTO(
        Long id,
        GestacaoDTO gestacaoId,
        OvinoDTO ovelhaMaeId,
        OvinoDTO ovelhaPaiId,
        LocalDateTime dataParto) {
}
