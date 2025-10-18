package br.com.genovi.infrastructure.mapper;

import br.com.genovi.domain.models.Ascendencia;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AscendenciaMapper {

    private final OvinoMapper ovinoMapper;

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
