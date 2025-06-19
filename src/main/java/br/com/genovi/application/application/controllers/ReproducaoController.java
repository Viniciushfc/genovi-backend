package br.com.genovi.application.application.controllers;

import br.com.genovi.application.dtos.CreateReproducaoDTO;
import br.com.genovi.application.dtos.ReproducaoDTO;
import br.com.genovi.application.application.services.ReproducaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reproducoes")
public class ReproducaoController {

    private final ReproducaoService reproducaoService;

    public ReproducaoController(ReproducaoService reproducaoService) {
        this.reproducaoService = reproducaoService;
    }

    @GetMapping
    public ResponseEntity<List<ReproducaoDTO>> findAll() {
        return ResponseEntity.ok(reproducaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReproducaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reproducaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReproducaoDTO> save(@RequestBody CreateReproducaoDTO dto) {
        ReproducaoDTO saved = reproducaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReproducaoDTO> update(@PathVariable Long id, @RequestBody CreateReproducaoDTO dto) {
        return ResponseEntity.ok(reproducaoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reproducaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
