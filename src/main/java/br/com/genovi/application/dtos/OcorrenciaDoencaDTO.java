package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Ovino;

import java.time.LocalDateTime;

public record OcorrenciaDoencaDTO(OvinoDTO ovino,
                                  DoencaDTO doenca,
                                  LocalDateTime dataInicio,
                                  LocalDateTime dataFinal,
                                  Boolean curado,
                                  UsuarioDTO responsavel) {
}
