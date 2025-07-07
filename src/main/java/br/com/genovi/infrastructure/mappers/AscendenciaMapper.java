package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import org.springframework.stereotype.Component;

@Component
public class AscendenciaMapper {

    private OvinoMapper ovinoMapper;

    public Ascendencia toEntity(CreateAscendenciaDTO dto, Ovino pai, Ovino mae) {
        return new Ascendencia(
                null,
                pai,
                mae
        );
    }

    public AscendenciaDTO toDTO(Ascendencia entity) {
        return new AscendenciaDTO(
                ovinoMapper.toDTO(entity.getPai()),
                ovinoMapper.toDTO(entity.getMae())
        );
    }

    public void updateEntityFromDTO(Ascendencia entity, Ovino pai, Ovino mae) {
        entity.setPai(pai);
        entity.setMae(mae);
    }
}
