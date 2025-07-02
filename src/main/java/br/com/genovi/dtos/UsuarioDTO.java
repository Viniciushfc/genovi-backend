package br.com.genovi.dtos;

import br.com.genovi.domain.enums.TypeUsuario;

public record UsuarioDTO(String username,
                         String email,
                         String senha,
                         TypeUsuario perfil,
                         Boolean autenticacao2fa) {
}
