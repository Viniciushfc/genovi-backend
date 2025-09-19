package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Registro;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.relatorios.CreateRegistroRecord;
import br.com.genovi.dtos.relatorios.RegistroRecord;
import org.springframework.stereotype.Component;

@Component
public class RegistroMapper {

    private final ReproducaoMapper reproducaoMapper;
    private final GestacaoMapper gestacaoMapper;
    private final PartoMapper partoMapper;
    private final AplicacaoMapper aplicacaoMapper;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;
    private final FuncionarioMapper funcionarioMapper;

    public RegistroMapper(ReproducaoMapper reproducaoMapper,
                          GestacaoMapper gestacaoMapper,
                          PartoMapper partoMapper,
                          AplicacaoMapper aplicacaoMapper,
                          OcorrenciaDoencaMapper ocorrenciaDoencaMapper,
                          FuncionarioMapper funcionarioMapper) {
        this.reproducaoMapper = reproducaoMapper;
        this.gestacaoMapper = gestacaoMapper;
        this.partoMapper = partoMapper;
        this.aplicacaoMapper = aplicacaoMapper;
        this.ocorrenciaDoencaMapper = ocorrenciaDoencaMapper;
        this.funcionarioMapper = funcionarioMapper;
    }

    public RegistroRecord toDTO(Registro registro) {

        return new RegistroRecord(
                registro.getId(),
                registro.getDataRegistro(),
                funcionarioMapper.toDTO(registro.getFuncionario()),
                reproducaoMapper.toDTO(registro.getReproducao()),
                gestacaoMapper.toDTO(registro.getGestacao()),
                partoMapper.toDTO(registro.getParto()),
                aplicacaoMapper.toDTO(registro.getAplicacao()),
                ocorrenciaDoencaMapper.toDTO(registro.getOcorrenciaDoenca())
        );
    }

    public Registro toEntity(CreateRegistroRecord dto,
                             Funcionario funcionario,
                             Reproducao reproducao,
                             Gestacao gestacao,
                             Parto parto,
                             Aplicacao aplicacao,
                             OcorrenciaDoenca ocorrenciaDoenca) {

        Registro registro = new Registro();
        registro.setDataRegistro(dto.dataRegistroId());
        registro.setFuncionario(funcionario);
        registro.setReproducao(reproducao);
        registro.setGestacao(gestacao);
        registro.setParto(parto);
        registro.setAplicacao(aplicacao);
        registro.setOcorrenciaDoenca(ocorrenciaDoenca);
        return registro;
    }
}
