package br.com.genovi.application.controllers;

import br.com.genovi.dtos.ciclo_cio.CicloCioDTO;
import br.com.genovi.dtos.ciclo_cio.CreateCicloCioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/ciclos-cio")
public interface CicloCioController {
    @GetMapping
    ResponseEntity<List<CicloCioDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<CicloCioDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<CicloCioDTO> save(@RequestBody CreateCicloCioDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<CicloCioDTO> update(@PathVariable Long id, @RequestBody CreateCicloCioDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}