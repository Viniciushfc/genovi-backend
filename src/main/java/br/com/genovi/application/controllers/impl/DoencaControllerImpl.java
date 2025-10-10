package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.DoencaController;
import br.com.genovi.dtos.doencas.CreateDoencaDTO;
import br.com.genovi.dtos.doencas.DoencaDTO;
import br.com.genovi.application.services.DoencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoencaControllerImpl implements DoencaController {

    private final DoencaService doencaService;

    public DoencaControllerImpl(DoencaService doencaService) {
        this.doencaService = doencaService;
    }

    @Override
    public ResponseEntity<List<DoencaDTO>> findAll() {
        return ResponseEntity.ok(doencaService.findAll());
    }

    @Override
    public ResponseEntity<DoencaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doencaService.findById(id));
    }

    @Override
    public ResponseEntity<DoencaDTO> save(@RequestBody CreateDoencaDTO dto) {
        DoencaDTO saved = doencaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<DoencaDTO> update(@PathVariable Long id, @RequestBody CreateDoencaDTO dto) {
        return ResponseEntity.ok(doencaService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doencaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}