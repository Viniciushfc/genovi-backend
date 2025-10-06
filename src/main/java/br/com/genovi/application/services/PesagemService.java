package br.com.genovi.application.services;

import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;

import java.util.List;

public interface PesagemService {
    List<PesagemDTO> findAll();

    PesagemDTO findById(Long id);

    PesagemDTO save(CreatePesagemDTO dto);

    PesagemDTO update(Long id, CreatePesagemDTO dto);

    void delete(Long id);
}