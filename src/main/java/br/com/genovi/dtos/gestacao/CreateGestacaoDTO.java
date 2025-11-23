package br.com.genovi.dtos.gestacao;

import java.time.LocalDateTime;

public record CreateGestacaoDTO(
        Long ovelhaMaeId,
        Long ovelhaPaiId,
        Long reproducaoId,
        LocalDateTime dataGestacao,
        Long idFuncionario) {
}
