package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.MedicamentoController;
import br.com.genovi.application.services.MedicamentoService;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicamentoControllerImpl implements MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoControllerImpl(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @Override
    public ResponseEntity<List<MedicamentoDTO>> findAll() {
        return ResponseEntity.ok(medicamentoService.findAll());
    }

    @Override
    public ResponseEntity<MedicamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.findById(id));
    }

    @Override
    public ResponseEntity<MedicamentoDTO> save(@RequestBody CreateMedicamentoDTO dto) {
        MedicamentoDTO saved = medicamentoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<MedicamentoDTO> update(@PathVariable Long id, @RequestBody CreateMedicamentoDTO dto) {
        return ResponseEntity.ok(medicamentoService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}