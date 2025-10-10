package br.com.genovi.application.controllers;

import br.com.genovi.application.services.AplicacaoService;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/aplicacoes")
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

    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoDTO> update(Long id, @RequestBody CreateAplicacaoDTO dto) {
        AplicacaoDTO saved = aplicacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aplicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
