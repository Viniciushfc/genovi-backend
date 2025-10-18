package br.com.genovi.infrastructure.mapper;

import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PartoMapper {

    private final GestacaoMapper gestacaoMapper;

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

        OvinoResumoDTO maeResumo = entity.getOvinoMae() != null
                ? new OvinoResumoDTO(entity.getOvinoMae().getId(), entity.getOvinoMae().getRfid(), entity.getOvinoMae().getNome(), entity.getOvinoMae().getFbb())
                : null;

        OvinoResumoDTO paiResumo = entity.getOvinoPai() != null
                ? new OvinoResumoDTO(entity.getOvinoPai().getId(), entity.getOvinoPai().getRfid(), entity.getOvinoPai().getNome(), entity.getOvinoPai().getFbb())
                : null;

        return new PartoDTO(
                entity.getId(),
                gestacaoMapper.toDTO(entity.getGestacao()),
                maeResumo,
                paiResumo,
                entity.getDataParto()
        );
    }
}
