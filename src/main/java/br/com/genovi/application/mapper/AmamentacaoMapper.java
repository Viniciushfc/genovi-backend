package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Amamentacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AmamentacaoMapper {

    //Converter DTO para Entidade
    public Amamentacao toEntity(CreateAmamentacaoDTO dto, Ovino ovelhaMae, Ovino carneiro) {
        return new Amamentacao(
                null,
                ovelhaMae,
                carneiro,
                dto.dataInicio(),
                dto.dataFim(),
                dto.observacoes()
        );
    }

    //Converter Entidade para DTO
    public AmamentacaoDTO toDTO(Amamentacao entity) {
        if (entity == null) {
            return null;
        }

        OvinoResumoDTO maeResumo = entity.getOvelhaMae() != null
                ? new OvinoResumoDTO(entity.getOvelhaMae().getId(), entity.getOvelhaMae().getRfid(), entity.getOvelhaMae().getNome(), entity.getOvelhaMae().getFbb())
                : null;

        OvinoResumoDTO cordeiroResumo = entity.getCordeiroMamando() != null
                ? new OvinoResumoDTO(entity.getCordeiroMamando().getId(), entity.getCordeiroMamando().getRfid(), entity.getCordeiroMamando().getNome(), entity.getCordeiroMamando().getFbb())
                : null;

        return new AmamentacaoDTO(
                maeResumo,
                cordeiroResumo,
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getObservacoes()
        );
    }
}
