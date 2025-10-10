package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.CompraController;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import br.com.genovi.application.services.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/compras")
public class CompraControllerImpl implements CompraController {

    private final CompraService compraService;

    public CompraControllerImpl(CompraService compraService) {
        this.compraService = compraService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CompraDTO>> findAll() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<CompraDTO> save(@RequestBody CreateCompraDTO dto) {
        CompraDTO saved = compraService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> update(@PathVariable Long id, @RequestBody CreateCompraDTO dto) {
        return ResponseEntity.ok(compraService.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
