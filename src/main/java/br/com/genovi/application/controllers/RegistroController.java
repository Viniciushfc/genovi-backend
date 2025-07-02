package br.com.genovi.application.controllers;

import br.com.genovi.application.services.RegistroService;
import br.com.genovi.dtos.RegistroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    //endpoint para utilização do relatorio do jasper aqui.
}
