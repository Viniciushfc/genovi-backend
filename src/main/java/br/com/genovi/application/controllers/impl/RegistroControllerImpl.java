package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.RegistroController;
import br.com.genovi.application.services.RegistroService;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/registros")
public class RegistroControllerImpl implements RegistroController {

    private final RegistroService registroService;

    public RegistroControllerImpl(RegistroService registroService) {
        this.registroService = registroService;
    }

    @Override
    public ResponseEntity<List<RegistroDTO>> findAll() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @Override
    public ResponseEntity<RegistroDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.findById(id));
    }

    @Override
    public ResponseEntity<RegistroDTO> save(@RequestBody CreateRegistroDTO dto) {
        RegistroDTO saved = registroService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<RegistroDTO> update(@PathVariable Long id, @RequestBody CreateRegistroDTO dto) {
        return ResponseEntity.ok(registroService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RegistroDTO> sugestaoParaRegistro(@PathVariable Long id, @RequestBody Boolean isSugestao) {
        return ResponseEntity.ok(registroService.sugestaoParaRegistro(id, isSugestao));
    }
}