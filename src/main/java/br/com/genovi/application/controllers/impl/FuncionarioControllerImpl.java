package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.FuncionarioController;
import br.com.genovi.application.services.FuncionarioService;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FuncionarioControllerImpl implements FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioControllerImpl(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Override
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        return ResponseEntity.ok(funcionarioService.findAll());
    }

    @Override
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    @Override
    public ResponseEntity<FuncionarioDTO> save(@RequestBody CreateFuncionarioDTO dto) {
        FuncionarioDTO saved = funcionarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody CreateFuncionarioDTO dto) {
        return ResponseEntity.ok(funcionarioService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}