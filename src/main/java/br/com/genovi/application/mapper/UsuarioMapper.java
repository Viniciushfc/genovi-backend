package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    private final FuncionarioMapper funcionarioMapper;

    public Usuario toEntity(CreateUsuarioDTO dto, Boolean ativo, Funcionario funcionario) {
        return new Usuario(
                null,
                ativo,
                dto.email(),
                dto.senha(),
                dto.autenticacao2fa(),
                dto.enumRoles(),
                funcionario
        );
    }

    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }

        return new UsuarioDTO(
                entity.getId(),
                entity.isAtivo(),
                entity.getEmail(),
                entity.getSenha(),
                entity.getAutenticacao2fa(),
                entity.getEnumRoles(),
                funcionarioMapper.toDTO(entity.getFuncionario())
        );
    }
}
