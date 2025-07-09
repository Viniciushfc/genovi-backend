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
    public AmamentacaoDTO toDTO(Amamentacao amamentacao) {
        return new AmamentacaoDTO(
                ovinoMapper.toDTO(amamentacao.getOvelhaMae()),
                ovinoMapper.toDTO(amamentacao.getCordeiroMamando()),
                amamentacao.getDataInicio(),
                amamentacao.getDataFim(),
                amamentacao.getObservacoes()
        );
    }

    //Atualizar uma entidade existente a partir do DTO
    public void updateEntityFromDTO(CreateAmamentacaoDTO dto, Amamentacao entity, Ovino ovelhaMae, Ovino carneiro) {
        entity.setOvelhaMae(ovelhaMae);
        entity.setCordeiroMamando(carneiro);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());
    }
}
