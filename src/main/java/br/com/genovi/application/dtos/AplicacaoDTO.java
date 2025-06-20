package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.models.Usuario;

import java.time.LocalDateTime;

public record AplicacaoDTO(LocalDateTime dataAplicacao,
                           OvinoDTO ovino,
                           MedicamentoDTO medicamento,
                           boolean temProximaDose,
                           LocalDateTime dataProximaDose,
                           UsuarioDTO responsavel,
                           String observacoes) {
}
