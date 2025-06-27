package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.RegistroDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistroMapper {

    private final OvinoMapper ovinoMapper;
    private final PartoMapper partoMapper;
    private final CicloCioMapper cicloCioMapper;
    private final AmamentacaoMapper amamentacaoMapper;
    private final ReproducaoMapper reproducaoMapper;
    private final AplicacaoMapper aplicacaoMapper;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    public RegistroMapper(OvinoMapper ovinoMapper, PartoMapper partoMapper, CicloCioMapper cicloCioMapper, AmamentacaoMapper amamentacaoMapper, ReproducaoMapper reproducaoMapper, AplicacaoMapper aplicacaoMapper, OcorrenciaDoencaMapper ocorrenciaDoencaMapper) {
        this.ovinoMapper = ovinoMapper;
        this.partoMapper = partoMapper;
        this.cicloCioMapper = cicloCioMapper;
        this.amamentacaoMapper = amamentacaoMapper;
        this.reproducaoMapper = reproducaoMapper;
        this.aplicacaoMapper = aplicacaoMapper;
        this.ocorrenciaDoencaMapper = ocorrenciaDoencaMapper;
    }

    public RegistroRecord toEntity(Ovino ovino, List<Parto> partos, List<CicloCio> cicloCios, List<Amamentacao> amamentacaos, List<Reproducao> reproducaos, List<Aplicacao> aplicacaos, List<OcorrenciaDoenca> ocorrenciaDoencas) {
        return new RegistroRecord(
                ovino,
                partos,
                cicloCios,
                amamentacaos,
                reproducaos,
                aplicacaos,
                ocorrenciaDoencas
        );
    }

    public RegistroDTO toDTO(RegistroRecord entity) {
        return new RegistroDTO(
                ovinoMapper.toDTO(entity.ovino()),
                entity.partos().stream().map(partoMapper::toDTO).toList(),
                entity.cicloCios().stream().map(cicloCioMapper::toDTO).toList(),
                entity.amamentacaos().stream().map(amamentacaoMapper::toDTO).toList(),
                entity.reproducaos().stream().map(reproducaoMapper::toDTO).toList(),
                entity.aplicacaos().stream().map(aplicacaoMapper::toDTO).toList(),
                entity.ocorrenciaDoencas().stream().map(ocorrenciaDoencaMapper::toDTO).toList()
        );
    }
}
