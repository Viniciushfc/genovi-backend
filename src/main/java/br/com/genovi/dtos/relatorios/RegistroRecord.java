package br.com.genovi.dtos.relatorios;


import br.com.genovi.domain.models.*;

import java.util.List;


public record RegistroRecord(Ovino ovino,
                             List<Parto> partos,
                             List<CicloCio> cicloCios,
                             List<Amamentacao> amamentacaos,
                             List<Reproducao> reproducaos,
                             List<Aplicacao> aplicacaos,
                             List<OcorrenciaDoenca> ocorrenciaDoencas) {


}
