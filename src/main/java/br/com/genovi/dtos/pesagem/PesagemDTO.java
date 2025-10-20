package br.com.genovi.dtos.pesagem;

import br.com.genovi.dtos.ovino.OvinoResumoDTO;

import java.time.LocalDateTime;

public record PesagemDTO(
        Long id,
        LocalDateTime dataPesagem,
        OvinoResumoDTO ovino,
        Double peso) {
}
