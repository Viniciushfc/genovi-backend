package br.com.genovi.application.services;

import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;

import java.util.List;

public interface FuncionarioService {
    List<FuncionarioDTO> findAll();

    FuncionarioDTO findById(Long id);

    FuncionarioDTO save(CreateFuncionarioDTO dto);

    FuncionarioDTO update(Long id, CreateFuncionarioDTO dto);

    void delete(Long id);
}