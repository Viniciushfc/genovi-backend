package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DoencaMapper {

    public Doenca toEntity(CreateDoencaDTO dto) {
        return new Doenca(
                null,
                dto.nome(),
                dto.descricao()
        );
    }

    public DoencaDTO toDTO(Doenca entity) {
        if (entity == null) {
            return null;
        }

        return new DoencaDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao()
        );
    }
}
