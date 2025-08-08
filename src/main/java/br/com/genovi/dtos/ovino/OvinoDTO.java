package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.Criador;
import br.com.genovi.dtos.CriadorDTO;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;

import java.time.LocalDateTime;

public record OvinoDTO(
        Long rfid,
        String nome,
        String raca,
        String fbb,
        LocalDateTime dataNascimento,
        CriadorDTO criador,
        int tempoFazendo,
        TypeGrauPureza typeGrauPureza,
        TypeSexo sexo,
        Float peso,
        String comportamento,
        AscendenciaDTO ascendencia,
        TypeStatus status
) {
}
