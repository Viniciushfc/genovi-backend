package br.com.genovi.dtos.relatorios;

import java.time.LocalDateTime;

public record CreateRegistroRecord(
        LocalDateTime dataRegistroId,
        Long funcionarioId,
        Long reproducaoId,
        Long gestacaoId,
        Long partoId,
        Long aplicacaoId,
        Long ocorrenciaDoencaId) {
}
