package br.com.genovi.application.controllers;

import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/auth/usuarios")
public interface UsuarioController {
    @GetMapping
    ResponseEntity<List<UsuarioDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<UsuarioDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<UsuarioDTO> save(@RequestBody CreateUsuarioDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody CreateUsuarioDTO dto);

    @PatchMapping("/{id}/disable")
    ResponseEntity<Void> disable(@PathVariable Long id);
}