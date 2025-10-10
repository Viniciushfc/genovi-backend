package br.com.genovi.application.controllers;

import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/doencas")
public interface DoencaController {
    @GetMapping
    ResponseEntity<List<DoencaDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<DoencaDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<DoencaDTO> save(@RequestBody CreateDoencaDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<DoencaDTO> update(@PathVariable Long id, @RequestBody CreateDoencaDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}