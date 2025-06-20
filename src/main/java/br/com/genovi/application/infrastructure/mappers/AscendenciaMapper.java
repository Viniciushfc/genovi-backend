package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Ascendencia;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.AscendenciaDTO;
import br.com.genovi.application.dtos.CreateAmamentacaoDTO;
import br.com.genovi.application.dtos.CreateAscendenciaDTO;
import org.springframework.stereotype.Component;

@Component
public class AscendenciaMapper {

    public Ascendencia toEntity(CreateAscendenciaDTO dto, Ovino pai, Ovino mae) {
        return new Ascendencia(
                null,
                pai,
                mae
        );
    }

    public AscendenciaDTO toDTO(Ascendencia entity) {
        return new AscendenciaDTO(
                entity.getPai(),
                entity.getMae()
        );
    }

    public void updateEntityFromDTO(Ascendencia entity, Ovino pai, Ovino mae ) {
        entity.setPai(pai);
        entity.setMae(mae);
    }
}
