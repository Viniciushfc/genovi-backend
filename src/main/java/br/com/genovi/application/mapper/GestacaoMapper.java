package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GestacaoMapper {

    private final ReproducaoMapper reproducaoMapper;

    public Gestacao toEntity(CreateGestacaoDTO dto, Ovino ovelhaMae, Ovino ovelhaPai, Reproducao reproducao) {
        return new Gestacao(
                null,
                ovelhaMae,
                ovelhaPai,
                reproducao,
                dto.dataGestacao()
        );
    }

    public GestacaoDTO toDTO(Gestacao entity) {
        if (entity == null) {
            return null;
        }

        OvinoResumeDTO maeResumo = entity.getOvelhaMae() != null
                ? new OvinoResumeDTO(entity.getOvelhaMae().getId(), entity.getOvelhaMae().getRfid(), entity.getOvelhaMae().getNome(), entity.getOvelhaMae().getFbb())
                : null;

        OvinoResumeDTO paiResumo = entity.getOvelhaPai() != null
                ? new OvinoResumeDTO(entity.getOvelhaPai().getId(), entity.getOvelhaPai().getRfid(), entity.getOvelhaPai().getNome(), entity.getOvelhaPai().getFbb())
                : null;

        return new GestacaoDTO(
                entity.getId(),
                maeResumo,
                paiResumo,
                reproducaoMapper.toDTO(entity.getReproducao()),
                entity.getDataGestacao()
        );
    }
}
