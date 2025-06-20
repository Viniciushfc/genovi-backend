package br.com.genovi.application.application.controllers;

import br.com.genovi.application.dtos.CreateOcorrenciaDoencaDTO;
import br.com.genovi.application.dtos.OcorrenciaDoencaDTO;
import br.com.genovi.application.application.services.OcorrenciaDoencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias-doencas")
public class OcorrenciaDoencaController {

    private final OcorrenciaDoencaService ocorrenciaDoencaService;

    public OcorrenciaDoencaController(OcorrenciaDoencaService ocorrenciaDoencaService) {
        this.ocorrenciaDoencaService = ocorrenciaDoencaService;
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaDoencaDTO>> findAll() {
        return ResponseEntity.ok(ocorrenciaDoencaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDoencaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaDoencaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OcorrenciaDoencaDTO> save(@RequestBody CreateOcorrenciaDoencaDTO dto) {
        OcorrenciaDoencaDTO saved = ocorrenciaDoencaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDoencaDTO> update(@PathVariable Long id, @RequestBody CreateOcorrenciaDoencaDTO dto) {
        return ResponseEntity.ok(ocorrenciaDoencaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ocorrenciaDoencaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
