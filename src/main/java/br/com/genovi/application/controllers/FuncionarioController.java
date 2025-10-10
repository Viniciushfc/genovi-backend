package br.com.genovi.application.controllers;

import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/funcionarios")
public interface FuncionarioController {
    @GetMapping
    ResponseEntity<List<FuncionarioDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<FuncionarioDTO> save(@RequestBody CreateFuncionarioDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody CreateFuncionarioDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}