package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import org.springframework.stereotype.Component;

@Component
public class OcorrenciaDoencaMapper {

    private final UsuarioMapper usuarioMapper;
    private final OvinoMapper ovinoMapper;
    private final DoencaMapper doencaMapper;

    public OcorrenciaDoencaMapper(UsuarioMapper usuarioMapper, OvinoMapper ovinoMapper, DoencaMapper doencaMapper) {
        this.usuarioMapper = usuarioMapper;
        this.ovinoMapper = ovinoMapper;
        this.doencaMapper = doencaMapper;
    }

    public OcorrenciaDoenca toEntity(CreateOcorrenciaDoencaDTO dto, Ovino ovino, Doenca doenca, Usuario usuario) {
        return new OcorrenciaDoenca(
                null,
                ovino,
                doenca,
                dto.dataInicio(),
                dto.dataFinal(),
                dto.curado(),
                usuario
        );
    }

    public OcorrenciaDoencaDTO toDTO(OcorrenciaDoenca entity) {
        return new OcorrenciaDoencaDTO(
                ovinoMapper.toDTO(entity.getOvino()),
                doencaMapper.toDTO(entity.getDoenca()),
                entity.getDataInicio(),
                entity.getDataFinal(),
                entity.isCurada(),
                usuarioMapper.toDTO(entity.getResponsavel()));
    }

    public void updateEntityFromDTO(CreateOcorrenciaDoencaDTO dto, OcorrenciaDoenca entity, Ovino ovino, Doenca doenca, Usuario usuario) {
        entity.setOvino(ovino);
        entity.setDoenca(doenca);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFinal(dto.dataFinal());
        entity.setCurada(dto.curado());
        entity.setResponsavel(usuario);
    }
}
