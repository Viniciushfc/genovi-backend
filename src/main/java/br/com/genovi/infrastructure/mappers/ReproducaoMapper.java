package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import org.springframework.stereotype.Component;

@Component
public class ReproducaoMapper {

    private final OvinoMapper ovinoMapper;

    public ReproducaoMapper(OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }

    public Reproducao toEntity(CreateReproducaoDTO dto, Ovino carneiroPai, Ovino ovelhaMae) {
        return new Reproducao(
                null,
                carneiroPai,
                ovelhaMae,
                dto.typeReproducao(),
                dto.dataReproducao(),
                dto.observacoes()
        );
    }

    public ReproducaoDTO toDTO(Reproducao entity) {
        if (entity == null) {
            return null;
        }

        return new ReproducaoDTO(
                entity.getId(),
                entity.getDataReproducao(),
                ovinoMapper.toDTO(entity.getCarneiroPai()),
                ovinoMapper.toDTO(entity.getOvelhaMae()),
                entity.getTypeReproducao(),
                entity.getObservacoes()
        );
    }

    public void updateEntetyFromDTO(CreateReproducaoDTO dto, Reproducao entity, Ovino carneiroPai, Ovino ovelhaMae) {
        entity.setDataReproducao(dto.dataReproducao());
        entity.setCarneiroPai(carneiroPai);
        entity.setOvelhaMae(ovelhaMae);
        entity.setTypeReproducao(dto.typeReproducao());
        entity.setObservacoes(dto.observacoes());
    }
}
