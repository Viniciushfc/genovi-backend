package br.com.genovi.application.controllers;

import br.com.genovi.dtos.criador.CriadorDTO;
import br.com.genovi.application.services.CriadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/criadores")
public class CriadorController {

    private final CriadorService criadorService;

    public CriadorController(CriadorService criadorService) {
        this.criadorService = criadorService;
    }

    @GetMapping
    public ResponseEntity<List<CriadorDTO>> findAll() {
        return ResponseEntity.ok(criadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriadorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(criadorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CriadorDTO> save(@RequestBody CriadorDTO dto) {
        CriadorDTO saved = criadorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriadorDTO> update(@PathVariable Long id, @RequestBody CriadorDTO dto) {
        return ResponseEntity.ok(criadorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        criadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
