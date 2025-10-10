package br.com.genovi.application.controllers;

import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/gestacoes")
public interface GestacaoController {
    @GetMapping
    ResponseEntity<List<GestacaoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<GestacaoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<GestacaoDTO> save(@RequestBody CreateGestacaoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<GestacaoDTO> update(@PathVariable Long id, @RequestBody CreateGestacaoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}