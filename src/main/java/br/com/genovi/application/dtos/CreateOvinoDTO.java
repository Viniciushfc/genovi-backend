package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.enums.TypeGrauPureza;
import br.com.genovi.application.domain.enums.TypeSexo;
import br.com.genovi.application.domain.enums.TypeStatus;

import java.time.LocalDateTime;

public record CreateOvinoDTO (Long rfid,
                              String nome,
                              String raca,
                              String fbb,
                              LocalDateTime dataNascimento,
                              Long criadorId,
                              int tempoFazendo,
                              TypeGrauPureza typeGrauPureza,
                              TypeSexo sexo,
                              Float peso,
                              String comportamento,
                              Long ascendenciaId,
                              TypeStatus status){
}
