package br.com.genovi.application.controllers;

import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/compras")
public interface CompraController {
    @GetMapping
    ResponseEntity<List<CompraDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<CompraDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<CompraDTO> save(@RequestBody CreateCompraDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<CompraDTO> update(@PathVariable Long id, @RequestBody CreateCompraDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}