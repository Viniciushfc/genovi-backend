package br.com.genovi.application.controllers;

import br.com.genovi.application.services.FuncionarioService;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        return ResponseEntity.ok(funcionarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> save(@RequestBody CreateFuncionarioDTO dto) {
        FuncionarioDTO saved = funcionarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody CreateFuncionarioDTO dto) {
        return ResponseEntity.ok(funcionarioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
