package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final FuncionarioMapper funcionarioMapper;

    public UsuarioMapper(FuncionarioMapper funcionarioMapper) {
        this.funcionarioMapper = funcionarioMapper;
    }

    public Usuario toEntity(CreateUsuarioDTO dto, Boolean ativo, Funcionario funcionario) {
        return new Usuario(
                null,
                ativo,
                dto.email(),
                dto.senha(),
                dto.autenticacao2fa(),
                null,
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
                funcionarioMapper.toDTO(entity.getFuncionario())
        );
    }

    public void updateEntityFromDTO(CreateUsuarioDTO dto, Usuario entity, Funcionario funcionario) {
        entity.setAtivo(dto.ativo());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setAutenticacao2fa(dto.autenticacao2fa());
        entity.setFuncionario(funcionario);
    }
}
