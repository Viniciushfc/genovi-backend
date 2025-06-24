package br.com.genovi.application.dtos;

import br.com.genovi.application.domain.enums.TypeUsuario;

public record UsuarioDTO(String username,
                         String email,
                         String senha,
                         TypeUsuario perfil,
                         Boolean autenticacao2fa) {
}
