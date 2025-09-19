package br.com.genovi.application.controllers;

import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import br.com.genovi.application.services.PesagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/pesagens")
public class PesagemController {

    private final PesagemService pesagemService;

    public PesagemController(PesagemService pesagemService) {
        this.pesagemService = pesagemService;
    }

    @GetMapping
    public ResponseEntity<List<PesagemDTO>> findAll() {
        return ResponseEntity.ok(pesagemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PesagemDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pesagemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PesagemDTO> save(@RequestBody CreatePesagemDTO dto) {
        PesagemDTO saved = pesagemService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PesagemDTO> update(@PathVariable Long id, @RequestBody CreatePesagemDTO dto) {
        return ResponseEntity.ok(pesagemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pesagemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
