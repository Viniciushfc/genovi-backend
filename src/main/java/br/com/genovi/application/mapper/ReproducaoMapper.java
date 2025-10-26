package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReproducaoMapper {

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

        OvinoResumeDTO paiResumo = entity.getCarneiroPai() != null
                ? new OvinoResumeDTO(entity.getCarneiroPai().getId(), entity.getCarneiroPai().getRfid(), entity.getCarneiroPai().getNome(), entity.getCarneiroPai().getFbb())
                : null;

        OvinoResumeDTO maeResumo = entity.getOvelhaMae() != null
                ? new OvinoResumeDTO(entity.getOvelhaMae().getId(), entity.getOvelhaMae().getRfid(), entity.getOvelhaMae().getNome(), entity.getOvelhaMae().getFbb())
                : null;

        return new ReproducaoDTO(
                entity.getId(),
                entity.getDataReproducao(),
                paiResumo,
                maeResumo,
                entity.getTypeReproducao(),
                entity.getObservacoes()
        );
    }
}
