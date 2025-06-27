package br.com.genovi.dtos.parto;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePartoDTO(Long ovelhaMaeId,
                             List<Long> animaisCriadosIds,
                             LocalDateTime dataParto,
                             int numeroCrias,
                             String observacoes,
                             boolean rejeicaoMaterna,
                             Long reproducaoOrigemId)  {
}
