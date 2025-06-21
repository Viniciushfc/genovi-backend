package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Reproducao;

import java.time.LocalDateTime;
import java.util.List;

public record PartoDTO(OvinoDTO ovelhaMae,
                       List<OvinoDTO> animaisCriados,
                       LocalDateTime dataParto,
                       int numeroCrias,
                       String observacoes,
                       boolean rejeicaoMaterna,
                       Reproducao reproducaoOrigem) {
}
