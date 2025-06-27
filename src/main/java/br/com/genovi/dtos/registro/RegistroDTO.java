package br.com.genovi.dtos.registro;

import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.util.List;

public record RegistroDTO(OvinoDTO ovino,
                          List<PartoDTO> partos,
                          List<CicloCioDTO> cicloCios,
                          List<AmamentacaoDTO> amamentacoes,
                          List<ReproducaoDTO> reproducoes,
                          List<AplicacaoDTO> aplicacoes,
                          List<OcorrenciaDoencaDTO> ocorrenciaDoencas) {
}
