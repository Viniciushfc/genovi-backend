package br.com.genovi.application.application.controllers;

import br.com.genovi.application.application.services.RegistroService;
import br.com.genovi.application.dtos.RegistroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/{ovinoId}")
    public ResponseEntity<RegistroDTO> gerarRegistroPorOvino(@PathVariable Long ovinoId) {
        RegistroDTO registroDTO = registroService.gerarRegistroPorOvinoById(ovinoId);
        return ResponseEntity.ok(registroDTO);
    }
}
