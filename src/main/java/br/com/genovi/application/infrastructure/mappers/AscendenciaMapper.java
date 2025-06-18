package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Ascendencia;
import br.com.genovi.application.dtos.AscendenciaDTO;
import br.com.genovi.application.dtos.CreateAscendenciaDTO;
import org.springframework.stereotype.Component;

@Component
public class AscendenciaMapper {

    public Ascendencia toEntity(CreateAscendenciaDTO dto) {
        return new Ascendencia(
                null,
                null,
                null //seram setados na service
        );
    }

    public AscendenciaDTO toDTO(Ascendencia entity) {
        return new AscendenciaDTO(
                entity.getPai(),
                entity.getMae()
        );
    }
}
