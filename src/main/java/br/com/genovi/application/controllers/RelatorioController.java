package br.com.genovi.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/user/relatorios")
public interface RelatorioController {
    @GetMapping("/ovino/{id}")
    ResponseEntity<byte[]> gerarRelatorioOvino(@PathVariable Long id);
}