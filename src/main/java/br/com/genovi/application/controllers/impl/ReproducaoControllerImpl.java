package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.ReproducaoController;
import br.com.genovi.dtos.reproducao.CreateReproducaoDTO;
import br.com.genovi.dtos.reproducao.ReproducaoDTO;
import br.com.genovi.application.services.ReproducaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReproducaoControllerImpl implements ReproducaoController {

    private final ReproducaoService reproducaoService;

    public ReproducaoControllerImpl(ReproducaoService reproducaoService) {
        this.reproducaoService = reproducaoService;
    }

    @Override
    public ResponseEntity<List<ReproducaoDTO>> findAll() {
        return ResponseEntity.ok(reproducaoService.findAll());
    }

    @Override
    public ResponseEntity<ReproducaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reproducaoService.findById(id));
    }

    @Override
    public ResponseEntity<ReproducaoDTO> save(@RequestBody CreateReproducaoDTO dto) {
        ReproducaoDTO saved = reproducaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<ReproducaoDTO> update(@PathVariable Long id, @RequestBody CreateReproducaoDTO dto) {
        return ResponseEntity.ok(reproducaoService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reproducaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}