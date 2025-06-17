package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Amamentacao;
import br.com.genovi.application.dtos.AmamentacaoDTO;
import br.com.genovi.application.dtos.CreateAmamentacaoDTO;
import org.springframework.stereotype.Component;

@Component
public class AmamentacaoMapper {

    //Converter DTO para Entidade
    public Amamentacao toEntity(CreateAmamentacaoDTO dto) {
        return new Amamentacao(
                null,
                null, //sera setado na service
                null, //sera setado na service
                dto.dataInicio(),
                dto.dataFim(),
                dto.observacoes()
        );
    }

    //Converter Entidade para DTO
    public AmamentacaoDTO toDTO(Amamentacao amamentacao) {
        return new AmamentacaoDTO(
                amamentacao.getOvelhaMae(),
                amamentacao.getCordeiroMamando(),
                amamentacao.getDataInicio(),
                amamentacao.getDataFim(),
                amamentacao.getObservacoes()
        );
    }

    //Atualizar uma entidade existente a partir do DTO
    public void updateEntityFromDTO(CreateAmamentacaoDTO dto, Amamentacao entity) {
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());
    }
}
