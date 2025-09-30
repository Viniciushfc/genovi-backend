package br.com.genovi.dtos.parto;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePartoDTO(Long ovelhaMaeId,
                             Long ovelhaPaiId,
                             Long gestacaoId,
                             LocalDateTime dataParto) {
}
