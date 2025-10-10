package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.PartoController;
import br.com.genovi.dtos.parto.CreatePartoDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.application.services.PartoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PartoControllerImpl implements PartoController {

    private final PartoService partoService;

    public PartoControllerImpl(PartoService partoService) {
        this.partoService = partoService;
    }

    @Override
    public ResponseEntity<List<PartoDTO>> findAll() {
        return ResponseEntity.ok(partoService.findAll());
    }

    @Override
    public ResponseEntity<PartoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(partoService.findById(id));
    }

    @Override
    public ResponseEntity<PartoDTO> save(@RequestBody CreatePartoDTO dto) {
        PartoDTO saved = partoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<PartoDTO> update(@PathVariable Long id, @RequestBody CreatePartoDTO dto) {
        return ResponseEntity.ok(partoService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}