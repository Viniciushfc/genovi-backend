package br.com.genovi.application.services;

import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.relatorios.GenealogiaDTO;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

    private final OvinoService ovinoService;

    public RelatorioService(OvinoService ovinoService) {
        this.ovinoService = ovinoService;
    }

    public byte[] gerarRelatorioRegistro(Long id) throws Exception {
        OvinoDTO ovino = ovinoService.findById(id);

        //le o arquivo .jrxml
        InputStream inputStream = new ClassPathResource("reports/relatorio-registro.jrxml").getInputStream();

        //compila o arquivo para .jasper
        JasperReport report = JasperCompileManager.compileReport(inputStream);

        //parametrização para inserção no relatorio
        Map<String, Object> parametros = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        parametros.put("title", "Registro de Ovino");
        parametros.put("nome", ovino.nome() != null ? ovino.nome() : "N/A");
        parametros.put("fbb", ovino.fbb() != null ? ovino.fbb() : "N/A");
        parametros.put("nascimento", ovino.dataNascimento() != null ? ovino.dataNascimento().format(formatter) : "N/A");

        parametros.put("rfid", ovino.rfid() != null ? String.valueOf(ovino.rfid()) : "N/A");
        parametros.put("pureza", ovino.typeGrauPureza() != null ? ovino.typeGrauPureza().name() : "N/A");
        parametros.put("raca", ovino.raca() != null ? ovino.raca() : "N/A");


        //Preenche o relatorio
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());

        //exporta pra pdf
        return JasperExportManager.exportReportToPdf(print);
    }

}

