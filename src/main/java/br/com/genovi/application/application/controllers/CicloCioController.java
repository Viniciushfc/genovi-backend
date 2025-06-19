package br.com.genovi.application.application.controllers;

import br.com.genovi.application.application.services.CicloCioService;
import br.com.genovi.application.dtos.CicloCioDTO;
import br.com.genovi.application.dtos.CreateCicloCioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciclos-cio")
public class CicloCioController {

    private final CicloCioService cicloCioService;

    public CicloCioController(CicloCioService cicloCioService) {
        this.cicloCioService = cicloCioService;
    }

    @GetMapping
    public ResponseEntity<List<CicloCioDTO>> findAll() {
        return ResponseEntity.ok(cicloCioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CicloCioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cicloCioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CicloCioDTO> save(@RequestBody CreateCicloCioDTO dto) {
        CicloCioDTO saved = cicloCioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CicloCioDTO> update(@PathVariable Long id, @RequestBody CreateCicloCioDTO dto) {
        return ResponseEntity.ok(cicloCioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cicloCioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

