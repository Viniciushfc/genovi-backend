package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartoMapper {

    @Lazy
    private final OvinoMapper ovinoMapper;
    private final GestacaoMapper gestacaoMapper;

    public PartoMapper(OvinoMapper ovinoMapper, GestacaoMapper gestacaoMapper) {
        this.ovinoMapper = ovinoMapper;
        this.gestacaoMapper = gestacaoMapper;
    }

    public Parto toEntity(Ovino ovinoMae, Ovino ovinoPai, Gestacao gestacao) {
        return new Parto(
                null,
                gestacao,
                ovinoPai,
                ovinoMae
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
                ovinoMapper.toDTO(entity.getOvinoPai())
        );
    }

    public void updateEntity(Parto entity, Ovino ovinoMae, Ovino ovinoPai, Gestacao gestacao) {
        entity.setGestacao(gestacao);
        entity.setOvinoMae(ovinoMae);
        entity.setOvinoPai(ovinoPai);
    }
}
