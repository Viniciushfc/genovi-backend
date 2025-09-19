package br.com.genovi.dtos.gestacao;

public record CreateGestacaoDTO(
        Long ovelhaMaeId,
        Long ovelhaPaiId,
        Long reproducaoId) {
}
