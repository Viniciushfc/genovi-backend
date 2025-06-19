package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Doenca;
import br.com.genovi.application.dtos.DoencaDTO;
import org.springframework.stereotype.Component;

@Component
public class DoencaMapper {

    public Doenca toEntity(DoencaDTO dto) {
        return new Doenca(
                null,
                dto.nome(),
                dto.descricao()
        );
    }

    public DoencaDTO toDTO(Doenca entity) {
        return new DoencaDTO(
                entity.getNome(),
                entity.getDescricao()
        );
    }

    public void updateEntityFromDto(DoencaDTO dto, Doenca entity) {
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
    }
}
