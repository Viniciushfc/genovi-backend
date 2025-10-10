package br.com.genovi.application.controllers;

import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/ascendencias")
public interface AscendenciaController {
    @GetMapping
    ResponseEntity<List<AscendenciaDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<AscendenciaDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<AscendenciaDTO> save(@RequestBody CreateAscendenciaDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<AscendenciaDTO> update(@PathVariable Long id, @RequestBody CreateAscendenciaDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}