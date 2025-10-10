package br.com.genovi.application.controllers;

import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/vendedores")
public interface VendedorController {
    @GetMapping
    ResponseEntity<List<VendedorDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<VendedorDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<VendedorDTO> save(@RequestBody CreateVendedorDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<VendedorDTO> update(@PathVariable Long id, @RequestBody CreateVendedorDTO dto);

    @PatchMapping("/{id}/disable")
    ResponseEntity<VendedorDTO> disable(@PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}