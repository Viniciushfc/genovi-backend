package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AscendenciaMapper {

    private final OvinoMapper ovinoMapper;

    //O @Lazy serve para atribuir um proxy ao ovinoMapper, para resolver o ciclo de dependencia apenas quando for usado.
    public AscendenciaMapper(@Lazy OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }


    public Ascendencia toEntity(CreateAscendenciaDTO dto, Ovino pai, Ovino mae) {
        return new Ascendencia(
                null,
                pai,
                mae
        );
    }

    public AscendenciaDTO toDTO(Ascendencia entity) {
        if (entity == null) {
            return null;
        }
        return new AscendenciaDTO(
                entity.getPai() != null ? ovinoMapper.toDTO(entity.getPai()) : null,
                entity.getMae() != null ? ovinoMapper.toDTO(entity.getMae()) : null
        );
    }
}
