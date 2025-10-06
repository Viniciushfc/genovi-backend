package br.com.genovi.application.services;

import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;

import java.util.List;

public interface OcorrenciaDoencaService {
    List<OcorrenciaDoencaDTO> findAll();

    OcorrenciaDoencaDTO findById(Long id);

    OcorrenciaDoencaDTO save(CreateOcorrenciaDoencaDTO dto);

    OcorrenciaDoencaDTO update(Long id, CreateOcorrenciaDoencaDTO dto);

    void delete(Long id);
}