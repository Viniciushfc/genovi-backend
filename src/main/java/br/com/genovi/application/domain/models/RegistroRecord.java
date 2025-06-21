package br.com.genovi.application.domain.models;


import java.util.List;


public record RegistroRecord(Ovino ovino,
                             List<Parto> partos,
                             List<CicloCio> cicloCios,
                             List<Amamentacao> amamentacaos,
                             List<Reproducao> reproducaos,
                             List<Aplicacao> aplicacaos,
                             List<OcorrenciaDoenca> ocorrenciaDoencas) {


}
