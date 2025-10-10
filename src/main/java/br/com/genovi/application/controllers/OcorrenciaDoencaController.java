package br.com.genovi.application.controllers;

import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/ocorrencias-doencas")
public interface OcorrenciaDoencaController {
    @GetMapping
    ResponseEntity<List<OcorrenciaDoencaDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<OcorrenciaDoencaDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<OcorrenciaDoencaDTO> save(@RequestBody CreateOcorrenciaDoencaDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<OcorrenciaDoencaDTO> update(@PathVariable Long id, @RequestBody CreateOcorrenciaDoencaDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}