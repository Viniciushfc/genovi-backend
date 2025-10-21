package br.com.genovi.dtos.registro;

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
