package br.com.genovi.application.controllers;

import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.application.services.DoencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/doencas")
public class DoencaController {

    private final DoencaService doencaService;

    public DoencaController(DoencaService doencaService) {
        this.doencaService = doencaService;
    }

    @GetMapping
    public ResponseEntity<List<DoencaDTO>> findAll() {
        return ResponseEntity.ok(doencaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoencaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doencaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DoencaDTO> save(@RequestBody CreateDoencaDTO dto) {
        DoencaDTO saved = doencaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoencaDTO> update(@PathVariable Long id, @RequestBody CreateDoencaDTO dto) {
        return ResponseEntity.ok(doencaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doencaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
