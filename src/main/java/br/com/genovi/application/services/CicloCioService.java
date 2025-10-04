package br.com.genovi.application.services;

import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;

import java.util.List;

public interface CicloCioService {
    List<CicloCioDTO> findAll();
    CicloCioDTO findById(Long id);
    CicloCioDTO save(CreateCicloCioDTO dto);
    CicloCioDTO update(Long id, CreateCicloCioDTO dto);
    void delete(Long id);
}