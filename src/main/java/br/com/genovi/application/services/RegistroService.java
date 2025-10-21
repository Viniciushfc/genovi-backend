package br.com.genovi.application.services;

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
}
