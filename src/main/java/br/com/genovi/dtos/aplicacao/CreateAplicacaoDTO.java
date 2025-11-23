package br.com.genovi.dtos.aplicacao;

import java.time.LocalDateTime;

public record CreateAplicacaoDTO(LocalDateTime dataAplicacao,
                                 Long ovinoId,
                                 Long medicamentoId,
                                 LocalDateTime dataProximaDose,
                                 Long idFuncionario) {
}
