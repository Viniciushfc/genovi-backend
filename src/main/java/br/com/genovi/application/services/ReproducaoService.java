package br.com.genovi.application.services;

import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;

import java.util.List;

public interface ReproducaoService {
    List<ReproducaoDTO> findAll();

    ReproducaoDTO findById(Long id);

    ReproducaoDTO save(CreateReproducaoDTO dto);

    ReproducaoDTO update(Long id, CreateReproducaoDTO dto);

    void delete(Long id);
}