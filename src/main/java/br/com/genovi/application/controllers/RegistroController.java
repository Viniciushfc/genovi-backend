package br.com.genovi.application.controllers;

import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/registros")
public interface RegistroController {
    @GetMapping
    ResponseEntity<List<RegistroDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<RegistroDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<RegistroDTO> save(@RequestBody CreateRegistroDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<RegistroDTO> update(@PathVariable Long id, @RequestBody CreateRegistroDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PatchMapping("/sugestao")
    ResponseEntity<RegistroDTO> sugestaoParaRegistro(@PathVariable Long id, @RequestBody Boolean isSugestao);
}
