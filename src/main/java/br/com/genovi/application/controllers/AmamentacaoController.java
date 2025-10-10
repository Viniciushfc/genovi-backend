package br.com.genovi.application.controllers;

import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/amamentacoes")
public interface AmamentacaoController {
    @GetMapping
    ResponseEntity<List<AmamentacaoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<AmamentacaoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<AmamentacaoDTO> save(@RequestBody CreateAmamentacaoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<AmamentacaoDTO> update(@PathVariable Long id, @RequestBody CreateAmamentacaoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}