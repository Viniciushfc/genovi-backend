package br.com.genovi.application.services;

import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;

import java.util.List;

public interface OvinoService {
    List<OvinoDTO> findAll();
    OvinoDTO findById(Long id);
    OvinoDTO save(CreateOvinoDTO dto);
    OvinoDTO update(Long id, CreateOvinoDTO dto);
    void disable(Long id);
}