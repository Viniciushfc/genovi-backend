package br.com.genovi.dtos.aplicacao;

import java.time.LocalDateTime;

public record CreateAplicacaoDTO(LocalDateTime dataAplicacao,
                                 Long ovinoId,
                                 Long medicamentoId,
                                 boolean temProximaDose,
                                 LocalDateTime dataProximaDose,
                                 Long responsavelId,
                                 String observacoes) {
}
