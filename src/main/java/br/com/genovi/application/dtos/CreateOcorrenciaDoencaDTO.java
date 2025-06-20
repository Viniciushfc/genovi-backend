package br.com.genovi.application.dtos;

import java.time.LocalDateTime;

public record CreateOcorrenciaDoencaDTO(Long ovinoId,
                                        Long doencaId,
                                        LocalDateTime dataInicio,
                                        LocalDateTime dataFinal,
                                        Boolean curado,
                                        Long responsavelId) {
}
