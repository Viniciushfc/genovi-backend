package br.com.genovi.application.services;

import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;

import java.util.List;

public interface PartoService {
    List<PartoDTO> findAll();
    PartoDTO findById(Long id);
    PartoDTO save(CreatePartoDTO dto);
    PartoDTO update(Long id, CreatePartoDTO dto);
    void delete(Long id);
}