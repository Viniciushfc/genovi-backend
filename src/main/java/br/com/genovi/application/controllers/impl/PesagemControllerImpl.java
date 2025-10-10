package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.PesagemController;
import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import br.com.genovi.application.services.PesagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PesagemControllerImpl implements PesagemController {

    private final PesagemService pesagemService;

    public PesagemControllerImpl(PesagemService pesagemService) {
        this.pesagemService = pesagemService;
    }

    @Override
    public ResponseEntity<List<PesagemDTO>> findAll() {
        return ResponseEntity.ok(pesagemService.findAll());
    }

    @Override
    public ResponseEntity<PesagemDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pesagemService.findById(id));
    }

    @Override
    public ResponseEntity<PesagemDTO> save(@RequestBody CreatePesagemDTO dto) {
        PesagemDTO saved = pesagemService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<PesagemDTO> update(@PathVariable Long id, @RequestBody CreatePesagemDTO dto) {
        return ResponseEntity.ok(pesagemService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pesagemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}