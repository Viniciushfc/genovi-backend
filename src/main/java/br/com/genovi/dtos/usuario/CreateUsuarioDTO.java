package br.com.genovi.dtos.usuario;

import br.com.genovi.domain.enums.EnumRole;

import java.util.Set;

public record CreateUsuarioDTO(Long id,
                               boolean ativo,
                               String email,
                               String senha,
                               Boolean autenticacao2fa,
                               Set<EnumRole> enumRoles,
                               Long funcionarioId) {
}
