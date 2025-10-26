package br.com.genovi.dtos.parto;

import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;

import java.time.LocalDateTime;

public record PartoDTO(
        Long id,
        GestacaoDTO gestacao,
        OvinoResumeDTO mae,
        OvinoResumeDTO pai,
        LocalDateTime dataParto) {
}
