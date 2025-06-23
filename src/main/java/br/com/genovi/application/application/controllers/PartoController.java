package br.com.genovi.application.application.controllers;

import br.com.genovi.application.dtos.CreatePartoDTO;
import br.com.genovi.application.dtos.PartoDTO;
import br.com.genovi.application.application.services.PartoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/partos")
public class PartoController {

    private final PartoService partoService;

    public PartoController(PartoService partoService) {
        this.partoService = partoService;
    }

    @GetMapping
    public ResponseEntity<List<PartoDTO>> findAll() {
        return ResponseEntity.ok(partoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(partoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PartoDTO> save(@RequestBody CreatePartoDTO dto) {
        PartoDTO saved = partoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartoDTO> update(@PathVariable Long id, @RequestBody CreatePartoDTO dto) {
        return ResponseEntity.ok(partoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
