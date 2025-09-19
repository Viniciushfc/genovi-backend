package br.com.genovi.application.controllers;

import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import br.com.genovi.application.services.VendedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> findAll() {
        return ResponseEntity.ok(vendedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vendedorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VendedorDTO> save(@RequestBody CreateVendedorDTO dto) {
        VendedorDTO saved = vendedorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> update(@PathVariable Long id, @RequestBody CreateVendedorDTO dto) {
        return ResponseEntity.ok(vendedorService.update(id, dto));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<VendedorDTO> disable(@PathVariable Long id) {
        vendedorService.disableById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
