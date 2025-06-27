package br.com.genovi.application.controllers;

import br.com.genovi.application.services.MedicamentoService;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoDTO>> findAll() {
        return ResponseEntity.ok(medicamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MedicamentoDTO> save(@RequestBody CreateMedicamentoDTO dto) {
        MedicamentoDTO saved = medicamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> update(@PathVariable Long id, @RequestBody CreateMedicamentoDTO dto) {
        return ResponseEntity.ok(medicamentoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

