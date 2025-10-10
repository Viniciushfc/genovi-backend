package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.AmamentacaoController;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import br.com.genovi.application.services.AmamentacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/amamentacoes")
public class AmamentacaoControllerImpl implements AmamentacaoController {

    private final AmamentacaoService amamentacaoService;

    public AmamentacaoControllerImpl(AmamentacaoService amamentacaoService) {
        this.amamentacaoService = amamentacaoService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AmamentacaoDTO>> findAll() {
        return ResponseEntity.ok(amamentacaoService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AmamentacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(amamentacaoService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<AmamentacaoDTO> save(@RequestBody CreateAmamentacaoDTO dto) {
        AmamentacaoDTO saved = amamentacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AmamentacaoDTO> update(@PathVariable Long id, @RequestBody CreateAmamentacaoDTO dto) {
        return ResponseEntity.ok(amamentacaoService.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        amamentacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
