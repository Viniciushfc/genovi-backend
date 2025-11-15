package br.com.genovi.dtos.registro;


import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.time.LocalDateTime;


public record RegistroDTO(
        Long idRegistro,
        LocalDateTime dataRegistro,
        Boolean isSugestao,
        FuncionarioDTO funcionario,
        ReproducaoDTO reproducao,
        GestacaoDTO gestacao,
        PartoDTO parto,
        AplicacaoDTO aplicacao,
        OcorrenciaDoencaDTO ocorrenciaDoenca,
        PesagemDTO pesagem) {

}
