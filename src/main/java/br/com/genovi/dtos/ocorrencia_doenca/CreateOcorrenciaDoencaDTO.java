package br.com.genovi.dtos.ocorrencia_doenca;

import java.time.LocalDateTime;

public record CreateOcorrenciaDoencaDTO(Long ovinoId,
                                        Long doencaId,
                                        LocalDateTime dataInicio,
                                        LocalDateTime dataFinal,
                                        Boolean curado,
                                        Long idFuncionario) {
}
