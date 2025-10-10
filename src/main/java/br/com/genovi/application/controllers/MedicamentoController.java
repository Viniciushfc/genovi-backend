package br.com.genovi.application.controllers;

import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/medicamentos")
public interface MedicamentoController {
    @GetMapping
    ResponseEntity<List<MedicamentoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<MedicamentoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<MedicamentoDTO> save(@RequestBody CreateMedicamentoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<MedicamentoDTO> update(@PathVariable Long id, @RequestBody CreateMedicamentoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}