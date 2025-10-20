package br.com.genovi.dtos;

import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.time.LocalDateTime;

public record RegistroDTO(Long id,
                          LocalDateTime dataRegistro,
                          FuncionarioDTO funcionario,
                          ReproducaoDTO reproducao,
                          GestacaoDTO gestacao,
                          PartoDTO parto,
                          AplicacaoDTO aplicacoes,
                          OcorrenciaDoencaDTO ocorrenciaDoencas) {
}
