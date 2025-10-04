package br.com.genovi.application.services;

import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;

import java.util.List;

public interface AscendenciaService {
    List<AscendenciaDTO> findAll();
    AscendenciaDTO findById(Long id);
    AscendenciaDTO save(CreateAscendenciaDTO dto);
    AscendenciaDTO update(Long id, CreateAscendenciaDTO dto);
    void delete(Long id);
}