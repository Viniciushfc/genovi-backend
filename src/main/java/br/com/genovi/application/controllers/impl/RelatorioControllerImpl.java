package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.RelatorioController;
import br.com.genovi.application.services.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelatorioControllerImpl implements RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioControllerImpl(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @Override
    public ResponseEntity<byte[]> gerarRelatorioOvino(@PathVariable Long id) {
        try {
            byte[] pdf = relatorioService.gerarRelatorioRegistro(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=relatorio-ovino-" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Erro ao gerar relatório: " + e.getMessage()).getBytes());
        }
    }
}