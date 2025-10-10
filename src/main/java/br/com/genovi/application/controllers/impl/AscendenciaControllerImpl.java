package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.AscendenciaController;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.ascendencia.CreateAscendenciaDTO;
import br.com.genovi.application.services.AscendenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/ascendencias")
public class AscendenciaControllerImpl implements AscendenciaController {

    private final AscendenciaService ascendenciaService;

    public AscendenciaControllerImpl(AscendenciaService ascendenciaService) {
        this.ascendenciaService = ascendenciaService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AscendenciaDTO>> findAll() {
        return ResponseEntity.ok(ascendenciaService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AscendenciaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ascendenciaService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<AscendenciaDTO> save(@RequestBody CreateAscendenciaDTO dto) {
        AscendenciaDTO saved = ascendenciaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AscendenciaDTO> update(@PathVariable Long id, @RequestBody CreateAscendenciaDTO dto) {
        return ResponseEntity.ok(ascendenciaService.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ascendenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
