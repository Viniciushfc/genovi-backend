package br.com.genovi.application.controllers;

import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/partos")
public interface PartoController {
    @GetMapping
    ResponseEntity<List<PartoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<PartoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<PartoDTO> save(@RequestBody CreatePartoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<PartoDTO> update(@PathVariable Long id, @RequestBody CreatePartoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}