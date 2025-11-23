package br.com.genovi.dtos.pesagem;

import java.time.LocalDateTime;

public record CreatePesagemDTO(
        LocalDateTime dataPesagem,
        Long idOvino,
        Double pesagem,
        Long idFuncionario) {
}
