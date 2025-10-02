package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Amamentacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import org.springframework.stereotype.Component;

@Component
public class AmamentacaoMapper {

    private final OvinoMapper ovinoMapper;

    public AmamentacaoMapper(OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }

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

        return new AmamentacaoDTO(
                ovinoMapper.toDTO(entity.getOvelhaMae()),
                ovinoMapper.toDTO(entity.getCordeiroMamando()),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getObservacoes()
        );
    }
}
