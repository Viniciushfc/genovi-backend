package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.OvinoController;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.application.services.OvinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OvinoControllerImpl implements OvinoController {

    private final OvinoService ovinoService;

    public OvinoControllerImpl(OvinoService ovinoService) {
        this.ovinoService = ovinoService;
    }

    @Override
    public ResponseEntity<List<OvinoDTO>> findAll() {
        return ResponseEntity.ok(ovinoService.findAll());
    }

    @Override
    public ResponseEntity<OvinoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ovinoService.findById(id));
    }

    @Override
    public ResponseEntity<OvinoDTO> save(@RequestBody CreateOvinoDTO dto) {
        OvinoDTO saved = ovinoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<OvinoDTO> update(@PathVariable Long id, @RequestBody CreateOvinoDTO dto) {
        return ResponseEntity.ok(ovinoService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        ovinoService.disable(id);
        return ResponseEntity.noContent().build();
    }
}