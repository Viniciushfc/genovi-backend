package br.com.genovi.application.services;

import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;

import java.util.List;

public interface AplicacaoService {
    List<AplicacaoDTO> findAll();
    AplicacaoDTO findById(Long id);
    AplicacaoDTO save(CreateAplicacaoDTO dto);
    void delete(Long id);
}