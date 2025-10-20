package br.com.genovi.dtos.relatorios;

import java.time.LocalDateTime;

public record CreateRegistroDTO(
        LocalDateTime dataRegistro,
        Long idFuncionario,
        Long idReproducao,
        Long idGestacao,
        Long idParto,
        Long idAplicacoes,
        Long idOcorrenciaDoencas) {
}
