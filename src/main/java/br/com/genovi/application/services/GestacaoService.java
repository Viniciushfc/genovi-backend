package br.com.genovi.application.services;

import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;

import java.util.List;

public interface GestacaoService {
    List<GestacaoDTO> findAll();

    GestacaoDTO findById(Long id);

    GestacaoDTO save(CreateGestacaoDTO dto);

    GestacaoDTO update(Long id, CreateGestacaoDTO dto);

    void delete(Long id);
}
