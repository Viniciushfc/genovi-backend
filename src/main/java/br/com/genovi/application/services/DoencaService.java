package br.com.genovi.application.services;

import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;

import java.util.List;

public interface DoencaService {
    List<DoencaDTO> findAll();
    DoencaDTO findById(Long id);
    DoencaDTO save(CreateDoencaDTO dto);
    DoencaDTO update(Long id, CreateDoencaDTO dto);
    void delete(Long id);
}