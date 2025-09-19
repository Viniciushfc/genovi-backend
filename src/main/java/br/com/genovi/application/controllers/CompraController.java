package br.com.genovi.application.controllers;

import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import br.com.genovi.application.services.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<CompraDTO>> findAll() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompraDTO> save(@RequestBody CreateCompraDTO dto) {
        CompraDTO saved = compraService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> update(@PathVariable Long id, @RequestBody CreateCompraDTO dto) {
        return ResponseEntity.ok(compraService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
