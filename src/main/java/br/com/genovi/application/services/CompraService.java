package br.com.genovi.application.services;

import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;

import java.util.List;

public interface CompraService {
    List<CompraDTO> findAll();

    CompraDTO findById(Long id);

    CompraDTO save(CreateCompraDTO dto);

    CompraDTO update(Long id, CreateCompraDTO dto);

    void delete(Long id);
}