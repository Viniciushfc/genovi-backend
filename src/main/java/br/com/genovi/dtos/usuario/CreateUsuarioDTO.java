package br.com.genovi.dtos.usuario;

public record CreateUsuarioDTO(Long id,
                               boolean ativo,
                               String email,
                               String senha,
                               Boolean autenticacao2fa,
                               Long funcionarioId) {
}
