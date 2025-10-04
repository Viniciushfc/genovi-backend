package br.com.genovi.application.services;

import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;

import java.util.List;

public interface VendedorService {
    List<VendedorDTO> findAll();
    VendedorDTO findById(Long id);
    VendedorDTO save(CreateVendedorDTO dto);
    VendedorDTO update(Long id, CreateVendedorDTO dto);
    void disableById(Long id);
    void delete(Long id);
}