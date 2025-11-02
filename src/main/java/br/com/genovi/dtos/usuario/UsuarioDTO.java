package br.com.genovi.dtos.usuario;

import br.com.genovi.domain.enums.EnumRole;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;

import java.util.Set;

public record UsuarioDTO(
        Long id,
        boolean ativo,
        String email,
        String senha,
        Boolean autenticacao2fa,
        Set<EnumRole> enumRoles,
        FuncionarioDTO funcionario) {
}
