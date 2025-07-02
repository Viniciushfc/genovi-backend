package br.com.genovi.dtos.aplicacao;

import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.UsuarioDTO;

import java.time.LocalDateTime;

public record AplicacaoDTO(LocalDateTime dataAplicacao,
                           OvinoDTO ovino,
                           MedicamentoDTO medicamento,
                           boolean temProximaDose,
                           LocalDateTime dataProximaDose,
                           UsuarioDTO responsavel,
                           String observacoes) {
}
