package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.GestacaoController;
import br.com.genovi.dtos.gestacao.CreateGestacaoDTO;
import br.com.genovi.dtos.gestacao.GestacaoDTO;
import br.com.genovi.application.services.GestacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GestacaoControllerImpl implements GestacaoController {

    private final GestacaoService gestacaoService;

    public GestacaoControllerImpl(GestacaoService gestacaoService) {
        this.gestacaoService = gestacaoService;
    }

    @Override
    public ResponseEntity<List<GestacaoDTO>> findAll() {
        return ResponseEntity.ok(gestacaoService.findAll());
    }

    @Override
    public ResponseEntity<GestacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gestacaoService.findById(id));
    }

    @Override
    public ResponseEntity<GestacaoDTO> save(@RequestBody CreateGestacaoDTO dto) {
        GestacaoDTO saved = gestacaoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<GestacaoDTO> update(@PathVariable Long id, @RequestBody CreateGestacaoDTO dto) {
        return ResponseEntity.ok(gestacaoService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gestacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}