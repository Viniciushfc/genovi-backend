package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Parto;
import br.com.genovi.application.domain.models.Reproducao;
import br.com.genovi.application.dtos.CreatePartoDTO;
import br.com.genovi.application.dtos.PartoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartoMapper {

    private final OvinoMapper ovinoMapper;

    public PartoMapper(OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }

    public Parto toEntity(CreatePartoDTO dto, Ovino ovino, List<Ovino> animaisCriados, Reproducao reproducao) {
        return new Parto(
                null,
                ovino,
                animaisCriados,
                dto.dataParto(),
                dto.numeroCrias(),
                dto.observacoes(),
                dto.rejeicaoMaterna(),
                reproducao
        );
    }

    public PartoDTO toDTO(Parto entity) {
        return new PartoDTO(
                ovinoMapper.toDTO(entity.getOvelhaMae()),
                entity.getAnimaisCriados().stream().map(ovinoMapper::toDTO).toList(),
                entity.getDataParto(),
                entity.getNumeroCrias(),
                entity.getObservacoes(),
                entity.isRejeicaoMaterna(),
                entity.getReproducaos()
        );
    }

    public void updateEntity(CreatePartoDTO dto, Parto entity, Ovino ovino, List<Ovino> animaisCriados, Reproducao reproducao) {
        entity.setOvelhaMae(ovino);
        entity.setAnimaisCriados(animaisCriados);
        entity.setDataParto(dto.dataParto());
        entity.setNumeroCrias(dto.numeroCrias());
        entity.setObservacoes(dto.observacoes());
        entity.setRejeicaoMaterna(dto.rejeicaoMaterna());
        entity.setReproducaos(reproducao);
    }
}
