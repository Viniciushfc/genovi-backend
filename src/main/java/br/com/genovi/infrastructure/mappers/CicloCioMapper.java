package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.CicloCio;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import org.springframework.stereotype.Component;

@Component
public class CicloCioMapper {

    public CicloCio toEntity(CreateCicloCioDTO dto, Ovino ovelha) {
        return new CicloCio(
                null,
                ovelha,
                dto.dataInicio(),
                dto.dataFim(),
                dto.observacoes()
        );
    }

    public CicloCioDTO toDTO(CicloCio entity) {
        return new CicloCioDTO(
                entity.getOvelha(),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getObservacoes()
        );
    }

    public void updateEntityFromDTO(CreateCicloCioDTO dto, CicloCio entity, Ovino ovelha) {
        entity.setOvelha(ovelha);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());
    }
}
