package br.com.genovi.dtos.amamentacao;

import br.com.genovi.dtos.ovino.OvinoResumeDTO;

import java.time.LocalDateTime;

public record AmamentacaoDTO(Long id,
                             OvinoResumeDTO ovelhaMae,
                             OvinoResumeDTO cordeiroMamando,
                             LocalDateTime dataInicio,
                             LocalDateTime dataFim,
                             String observacoes) {
}
