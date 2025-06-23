package br.com.genovi.application.application.controllers;

import br.com.genovi.application.application.services.AplicacaoService;
import br.com.genovi.application.dtos.AplicacaoDTO;
import br.com.genovi.application.dtos.CreateAplicacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/aplicacoes")
public class AplicacaoController {

    private final AplicacaoService aplicacaoService;

    public AplicacaoController(AplicacaoService aplicacaoService) {
        this.aplicacaoService = aplicacaoService;
    }

    @GetMapping
    public ResponseEntity<List<AplicacaoDTO>> findAll() {
        return ResponseEntity.ok(aplicacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aplicacaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AplicacaoDTO> save(@RequestBody CreateAplicacaoDTO dto) {
        AplicacaoDTO saved = aplicacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aplicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
