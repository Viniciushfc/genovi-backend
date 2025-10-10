package br.com.genovi.application.controllers;

import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/ovinos")
public interface OvinoController {
    @GetMapping
    ResponseEntity<List<OvinoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<OvinoDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<OvinoDTO> save(@RequestBody CreateOvinoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<OvinoDTO> update(@PathVariable Long id, @RequestBody CreateOvinoDTO dto);

    @PatchMapping("/{id}/disable")
    ResponseEntity<Void> disable(@PathVariable Long id);
}