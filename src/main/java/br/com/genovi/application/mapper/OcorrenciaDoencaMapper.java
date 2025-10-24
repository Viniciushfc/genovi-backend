package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OcorrenciaDoencaMapper {

    private final FuncionarioMapper funcionarioMapper;
    private final OvinoMapper ovinoMapper;
    private final DoencaMapper doencaMapper;

    public OcorrenciaDoenca toEntity(CreateOcorrenciaDoencaDTO dto, Ovino ovino, Doenca doenca) {

        return new OcorrenciaDoenca(
                null,
                ovino,
                doenca,
                dto.dataInicio(),
                dto.dataFinal(),
                dto.curado()
        );
    }

    public OcorrenciaDoencaDTO toDTO(OcorrenciaDoenca entity) {
        if (entity == null) {
            return null;
        }

        return new OcorrenciaDoencaDTO(
                ovinoMapper.toDTO(entity.getOvino()),
                doencaMapper.toDTO(entity.getDoenca()),
                entity.getDataInicio(),
                entity.getDataFinal(),
                entity.isCurada()
        );
    }
}
