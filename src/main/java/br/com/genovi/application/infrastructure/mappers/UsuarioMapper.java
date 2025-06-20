package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Usuario;
import br.com.genovi.application.dtos.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(
                null,
                null,
                dto.nome(),
                dto.email(),
                dto.senha(),
                dto.perfil(),
                dto.autenticacao2fa()
        );
    }

    public UsuarioDTO toDTO(Usuario entity) {
        return new UsuarioDTO(
                entity.getNome(),
                entity.getEmail(),
                entity.getSenha(),
                entity.getPerfil(),
                entity.getAutenticacao2fa()
        );
    }

    public void updateEntityFromDTO(UsuarioDTO dto, Usuario entity) {
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setPerfil(dto.perfil());
        entity.setAutenticacao2fa(dto.autenticacao2fa());
    }
}
