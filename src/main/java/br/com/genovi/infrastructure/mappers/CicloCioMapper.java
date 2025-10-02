package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.CicloCio;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import org.springframework.stereotype.Component;

@Component
public class CicloCioMapper {

    private final OvinoMapper ovinoMapper;

    public CicloCioMapper(OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }

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
        if (entity == null) {
            return null;
        }
        
        return new CicloCioDTO(
                ovinoMapper.toDTO(entity.getOvelha()),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getObservacoes()
        );
    }
}
