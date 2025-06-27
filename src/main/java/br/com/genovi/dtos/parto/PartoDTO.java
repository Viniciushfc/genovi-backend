package br.com.genovi.dtos.parto;

import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.ovino.OvinoDTO;

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
