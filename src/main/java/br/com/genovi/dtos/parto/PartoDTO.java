package br.com.genovi.dtos.parto;

import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;

import java.time.LocalDateTime;

public record PartoDTO(
        Long id,
        GestacaoDTO gestacao,
        OvinoResumoDTO mae,
        OvinoResumoDTO pai,
        LocalDateTime dataParto) {
}
