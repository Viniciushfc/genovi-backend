package br.com.genovi.application.controllers;

import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/aplicacoes")
public interface AplicacaoController {
    @GetMapping
    ResponseEntity<List<AplicacaoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<AplicacaoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<AplicacaoDTO> save(@RequestBody CreateAplicacaoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<AplicacaoDTO> update(Long id, @RequestBody CreateAplicacaoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}