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
        GenealogiaDTO genealogiaDTO = ovinoService.familyTree(ovino);

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
        parametros.put("sexo", ovino.sexo() != null ? ovino.sexo().name() : "N/A");
        parametros.put("rfid", ovino.rfid() != null ? String.valueOf(ovino.rfid()) : "N/A");
        parametros.put("pureza", ovino.typeGrauPureza() != null ? ovino.typeGrauPureza().name() : "N/A");
        parametros.put("raca", ovino.raca() != null ? ovino.raca() : "N/A");

        if (ovino.criador() != null) {
            parametros.put("criador_nome", ovino.criador().nome() != null ? ovino.criador().nome() : "N/A");
            parametros.put("criador_cpfCnpj", ovino.criador().cpfCnpj() != null ? ovino.criador().cpfCnpj() : "N/A");
            parametros.put("criador_endereco", ovino.criador().endereco() != null ? ovino.criador().endereco() : "N/A");
            parametros.put("criador_telefone", ovino.criador().telefone() != null ? ovino.criador().telefone() : "N/A");
        } else {
            parametros.put("criador_nome", "N/A");
            parametros.put("criador_cpfCnpj", "N/A");
            parametros.put("criador_endereco", "N/A");
            parametros.put("criador_telefone", "N/A");
        }

        // Safe genealogy mapping
        if (genealogiaDTO != null) {
            GenealogiaDTO pai = genealogiaDTO.carneiroPai();
            GenealogiaDTO mae = genealogiaDTO.ovelhaMae();

            parametros.put("carneiro_pai", pai != null && pai.ovino() != null ? pai.ovino().nome() : "N/A");
            parametros.put("ovelha_mae", mae != null && mae.ovino() != null ? mae.ovino().nome() : "N/A");

            if (pai != null) {
                parametros.put("carneiro_avo2", pai.carneiroPai());
                parametros.put("ovelha_avo2", pai.ovelhaMae());
            } else {
                parametros.put("carneiro_avo2", "N/A");
                parametros.put("ovelha_avo2", "N/A");
            }

            if (mae != null) {
                parametros.put("carneiro_avo", mae.carneiroPai());
                parametros.put("ovelha_avo", mae.ovelhaMae());
            } else {
                parametros.put("carneiro_avo", "N/A");
                parametros.put("ovelha_avo", "N/A");
            }
        } else {
            parametros.put("carneiro_pai", "N/A");
            parametros.put("ovelha_mae", "N/A");
            parametros.put("carneiro_avo", "N/A");
            parametros.put("ovelha_avo", "N/A");
            parametros.put("carneiro_avo2", "N/A");
            parametros.put("ovelha_avo2", "N/A");
        }


        //Preenche o relatorio
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());

        //exporta pra pdf
        return JasperExportManager.exportReportToPdf(print);
    }

}

