package br.com.genovi.dtos.usuario;

import br.com.genovi.domain.enums.TypeUsuario;
import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;

public record UsuarioDTO(
        Long id,
        boolean ativo,
        String email,
        String senha,
        Boolean autenticacao2fa,
        FuncionarioDTO funcionario) {
}
