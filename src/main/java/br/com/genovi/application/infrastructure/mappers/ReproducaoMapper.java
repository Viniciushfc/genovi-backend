package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Reproducao;
import br.com.genovi.application.dtos.CreateReproducaoDTO;
import br.com.genovi.application.dtos.ReproducaoDTO;
import org.springframework.stereotype.Component;

@Component
public class ReproducaoMapper {

    public Reproducao toEntity(CreateReproducaoDTO dto, Ovino carneiroPai, Ovino ovelhaMae) {
        return new Reproducao(
                null,
                dto.dataReproducao(),
                carneiroPai,
                ovelhaMae,
                dto.typeReproducao(),
                dto.observacoes()
        );
    }

    public ReproducaoDTO toDTO(Reproducao entity) {
        return new ReproducaoDTO(
                entity.getDataReproducao(),
                entity.getCarneiroPai(),
                entity.getOvelhaMae(),
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
