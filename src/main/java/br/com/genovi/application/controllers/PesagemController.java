package br.com.genovi.application.controllers;

import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/pesagens")
public interface PesagemController {
    @GetMapping
    ResponseEntity<List<PesagemDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<PesagemDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<PesagemDTO> save(@RequestBody CreatePesagemDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<PesagemDTO> update(@PathVariable Long id, @RequestBody CreatePesagemDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}