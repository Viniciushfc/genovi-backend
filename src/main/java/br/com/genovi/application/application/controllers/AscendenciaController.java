package br.com.genovi.application.application.controllers;

import br.com.genovi.application.dtos.AscendenciaDTO;
import br.com.genovi.application.dtos.CreateAscendenciaDTO;
import br.com.genovi.application.application.services.AscendenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/ascendencias")
public class AscendenciaController {

    private final AscendenciaService ascendenciaService;

    public AscendenciaController(AscendenciaService ascendenciaService) {
        this.ascendenciaService = ascendenciaService;
    }

    @GetMapping
    public ResponseEntity<List<AscendenciaDTO>> findAll() {
        return ResponseEntity.ok(ascendenciaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AscendenciaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ascendenciaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AscendenciaDTO> save(@RequestBody CreateAscendenciaDTO dto) {
        AscendenciaDTO saved = ascendenciaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AscendenciaDTO> update(@PathVariable Long id, @RequestBody CreateAscendenciaDTO dto) {
        return ResponseEntity.ok(ascendenciaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ascendenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
