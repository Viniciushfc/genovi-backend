package br.com.genovi.application.services;

import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;

import java.util.List;

public interface AmamentacaoService {

    List<AmamentacaoDTO> findAll();

    AmamentacaoDTO findById(Long id);

    AmamentacaoDTO save(CreateAmamentacaoDTO dto);

    AmamentacaoDTO update(Long id, CreateAmamentacaoDTO dto);

    void delete(Long id);
}