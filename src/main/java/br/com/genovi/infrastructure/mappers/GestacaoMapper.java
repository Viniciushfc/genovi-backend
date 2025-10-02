package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Gestacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Reproducao;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import org.springframework.stereotype.Component;

@Component
public class GestacaoMapper {

    private final OvinoMapper ovinoMapper;
    private final ReproducaoMapper reproducaoMapper;

    public GestacaoMapper(OvinoMapper ovinoMapper, ReproducaoMapper reproducaoMapper) {
        this.ovinoMapper = ovinoMapper;
        this.reproducaoMapper = reproducaoMapper;
    }

    public Gestacao toEntity(CreateGestacaoDTO dto, Ovino ovelhaMae, Ovino ovelhaPai, Reproducao reproducao) {
        return new Gestacao(
                null,
                ovelhaMae,
                ovelhaPai,
                reproducao,
                dto.dataGestacao()
        );
    }

    public GestacaoDTO toDTO(Gestacao entity) {
        if (entity == null) {
            return null;
        }

        return new GestacaoDTO(
                entity.getId(),
                ovinoMapper.toDTO(entity.getOvelhaMae()),
                ovinoMapper.toDTO(entity.getOvelhaPai()),
                reproducaoMapper.toDTO(entity.getReproducao()),
                entity.getDataGestacao()
        );
    }
}
