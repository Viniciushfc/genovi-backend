package br.com.genovi.application.controllers;

import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.application.services.GestacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/gestacoes")
public class GestacaoController {

    private final GestacaoService gestacaoService;

    public GestacaoController(GestacaoService gestacaoService) {
        this.gestacaoService = gestacaoService;
    }

    @GetMapping
    public ResponseEntity<List<GestacaoDTO>> findAll() {
        return ResponseEntity.ok(gestacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gestacaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GestacaoDTO> save(@RequestBody CreateGestacaoDTO dto) {
        GestacaoDTO saved = gestacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GestacaoDTO> update(@PathVariable Long id, @RequestBody CreateGestacaoDTO dto) {
        return ResponseEntity.ok(gestacaoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gestacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
