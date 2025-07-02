package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto, Boolean ativo) {
        return new Usuario(
                null,
                ativo,
                dto.username(),
                dto.email(),
                dto.senha(),
                dto.perfil(),
                dto.autenticacao2fa(),
                null
        );
    }

    public UsuarioDTO toDTO(Usuario entity) {
        return new UsuarioDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getSenha(),
                entity.getPerfil(),
                entity.getAutenticacao2fa()
        );
    }

    public void updateEntityFromDTO(UsuarioDTO dto, Usuario entity, Boolean ativo) {
        entity.setAtivo(ativo);
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setPerfil(dto.perfil());
        entity.setAutenticacao2fa(dto.autenticacao2fa());
    }
}
