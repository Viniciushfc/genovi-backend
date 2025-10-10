package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.OcorrenciaDoencaController;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.application.services.OcorrenciaDoencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OcorrenciaDoencaControllerImpl implements OcorrenciaDoencaController {

    private final OcorrenciaDoencaService ocorrenciaDoencaService;

    public OcorrenciaDoencaControllerImpl(OcorrenciaDoencaService ocorrenciaDoencaService) {
        this.ocorrenciaDoencaService = ocorrenciaDoencaService;
    }

    @Override
    public ResponseEntity<List<OcorrenciaDoencaDTO>> findAll() {
        return ResponseEntity.ok(ocorrenciaDoencaService.findAll());
    }

    @Override
    public ResponseEntity<OcorrenciaDoencaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaDoencaService.findById(id));
    }

    @Override
    public ResponseEntity<OcorrenciaDoencaDTO> save(@RequestBody CreateOcorrenciaDoencaDTO dto) {
        OcorrenciaDoencaDTO saved = ocorrenciaDoencaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<OcorrenciaDoencaDTO> update(@PathVariable Long id, @RequestBody CreateOcorrenciaDoencaDTO dto) {
        return ResponseEntity.ok(ocorrenciaDoencaService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ocorrenciaDoencaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}