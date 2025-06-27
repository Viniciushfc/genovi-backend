package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Criador;

import java.time.LocalDateTime;

public record OvinoDTO(
        Long rfid,
        boolean ativo,
        String nome,
        String raca,
        String fbb,
        LocalDateTime dataNascimento,
        Criador criador,
        int tempoFazendo,
        TypeGrauPureza typeGrauPureza,
        TypeSexo sexo,
        Float peso,
        String comportamento,
        Ascendencia ascendencia,
        TypeStatus status
) {
}
