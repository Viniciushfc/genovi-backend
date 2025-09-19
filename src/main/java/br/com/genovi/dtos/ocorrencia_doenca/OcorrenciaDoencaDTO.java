package br.com.genovi.dtos.ocorrencia_doenca;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;

import java.time.LocalDateTime;

public record OcorrenciaDoencaDTO(OvinoDTO ovino,
                                  DoencaDTO doenca,
                                  LocalDateTime dataInicio,
                                  LocalDateTime dataFinal,
                                  Boolean curado,
                                  FuncionarioDTO responsavel) {
}
