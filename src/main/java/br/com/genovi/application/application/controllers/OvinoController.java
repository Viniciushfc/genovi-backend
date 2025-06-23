package br.com.genovi.application.application.controllers;

import br.com.genovi.application.dtos.CreateOvinoDTO;
import br.com.genovi.application.dtos.OvinoDTO;
import br.com.genovi.application.application.services.OvinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/ovinos")
public class OvinoController {

    private final OvinoService ovinoService;

    public OvinoController(OvinoService ovinoService) {
        this.ovinoService = ovinoService;
    }

    @GetMapping
    public ResponseEntity<List<OvinoDTO>> findAll() {
        return ResponseEntity.ok(ovinoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OvinoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ovinoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OvinoDTO> save(@RequestBody CreateOvinoDTO dto) {
        OvinoDTO saved = ovinoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OvinoDTO> update(@PathVariable Long id, @RequestBody CreateOvinoDTO dto) {
        return ResponseEntity.ok(ovinoService.update(id, dto));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        ovinoService.disable(id);
        return ResponseEntity.noContent().build();
    }
}
