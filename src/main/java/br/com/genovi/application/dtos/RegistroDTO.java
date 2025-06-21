package br.com.genovi.application.dtos;

import java.util.List;

public record RegistroDTO(OvinoDTO ovino,
                          List<PartoDTO> partos,
                          List<CicloCioDTO> cicloCios,
                          List<AmamentacaoDTO> amamentacoes,
                          List<ReproducaoDTO> reproducoes,
                          List<AplicacaoDTO> aplicacoes,
                          List<OcorrenciaDoencaDTO> ocorrenciaDoencas) {
}
