package br.com.genovi.application.services;

import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;

import java.util.List;

public interface RegistroService {
    List<RegistroDTO> findAll();

    RegistroDTO findById(Long id);

    RegistroDTO save(CreateRegistroDTO dto);

    RegistroDTO update(Long id, CreateRegistroDTO dto);

    void delete(Long id);

    RegistroDTO sugestaoParaRegistro(Long id, Boolean isSugestao);

    void createReproducaoRegistro(Reproducao reproducao, Long idFuncionario);

    void createGestacaoRegistro(Gestacao gestacao, Long idFuncionario);

    void createPartoRegistro(Parto parto, Long idFuncionario);

    void createAplicacaoRegistro(Aplicacao aplicacao, Long idFuncionario);

    void createOcorrenciaDoencaRegistro(OcorrenciaDoenca ocorrenciaDoenca, Long idFuncionario);

    void createPesagemRegistro(Pesagem pesagem, Long idFuncionario);

}
