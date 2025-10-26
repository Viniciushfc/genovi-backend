package br.com.genovi.dtos.pesagem;

import br.com.genovi.dtos.ovino.OvinoResumeDTO;

import java.time.LocalDateTime;

public record PesagemDTO(
        Long id,
        LocalDateTime dataPesagem,
        OvinoResumeDTO ovino,
        Double peso) {
}
