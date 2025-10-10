package br.com.genovi.application.controllers;

import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/reproducoes")
public interface ReproducaoController {
    @GetMapping
    ResponseEntity<List<ReproducaoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<ReproducaoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<ReproducaoDTO> save(@RequestBody CreateReproducaoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<ReproducaoDTO> update(@PathVariable Long id, @RequestBody CreateReproducaoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}