package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistroMapper {

    private final ReproducaoMapper reproducaoMapper;
    private final GestacaoMapper gestacaoMapper;
    private final PartoMapper partoMapper;
    private final AplicacaoMapper aplicacaoMapper;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;
    private final FuncionarioMapper funcionarioMapper;
    private final PesagemMapper pesagemMapper;

    public RegistroDTO toDTO(Registro registro) {

        return new RegistroDTO(
                registro.getId(),
                registro.getDataRegistro(),
                registro.isSugestao(),
                funcionarioMapper.toDTO(registro.getFuncionario()),
                reproducaoMapper.toDTO(registro.getReproducao()),
                gestacaoMapper.toDTO(registro.getGestacao()),
                partoMapper.toDTO(registro.getParto()),
                aplicacaoMapper.toDTO(registro.getAplicacao()),
                ocorrenciaDoencaMapper.toDTO(registro.getOcorrenciaDoenca()),
                pesagemMapper.toDTO(registro.getPesagem())
        );
    }

    public Registro toEntity(CreateRegistroDTO dto,
                             Funcionario funcionario,
                             Reproducao reproducao,
                             Gestacao gestacao,
                             Parto parto,
                             Aplicacao aplicacao,
                             OcorrenciaDoenca ocorrenciaDoenca,
                             Pesagem pesagem) {

        Registro registro = new Registro();
        registro.setDataRegistro(dto.dataRegistro());
        registro.setSugestao(dto.isSugestao());
        registro.setFuncionario(funcionario);
        registro.setReproducao(reproducao);
        registro.setGestacao(gestacao);
        registro.setParto(parto);
        registro.setAplicacao(aplicacao);
        registro.setOcorrenciaDoenca(ocorrenciaDoenca);
        registro.setPesagem(pesagem);
        
        return registro;
    }
}
