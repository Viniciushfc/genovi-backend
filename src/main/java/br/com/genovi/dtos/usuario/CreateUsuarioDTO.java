package br.com.genovi.dtos.usuario;

import br.com.genovi.domain.enums.TypeUsuario;

public record CreateUsuarioDTO(Long id,
                               boolean ativo,
                               String email,
                               String senha,
                               Boolean autenticacao2fa,
                               Long funcionarioId) {
}
