package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PartoMapper {

    @Lazy
    private final OvinoMapper ovinoMapper;
    private final GestacaoMapper gestacaoMapper;

    public PartoMapper(OvinoMapper ovinoMapper, GestacaoMapper gestacaoMapper) {
        this.ovinoMapper = ovinoMapper;
        this.gestacaoMapper = gestacaoMapper;
    }

    public Parto toEntity(CreatePartoDTO dto, Ovino ovinoMae, Ovino ovinoPai, Gestacao gestacao) {
        return new Parto(
                null,
                ovinoPai,
                ovinoMae,
                gestacao,
                dto.dataParto()
        );
    }

    public PartoDTO toDTO(Parto entity) {
        if (entity == null) {
            return null;
        }

        return new PartoDTO(
                entity.getId(),
                gestacaoMapper.toDTO(entity.getGestacao()),
                ovinoMapper.toDTO(entity.getOvinoMae()),
                ovinoMapper.toDTO(entity.getOvinoPai()),
                entity.getDataParto()
        );
    }
}
