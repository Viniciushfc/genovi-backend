package br.com.genovi.infrastructure.geminiapi.service;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.OcorrenciaDoencaRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class GenoviDatabaseService {

    private final OvinoRepository ovinoRepository;
    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;

    public JsonObject fetchAnimalData(String rfidString) {
        Long rfid = Long.parseLong(rfidString);
        Ovino ovino = ovinoRepository.findByRfid(rfid);
        return getOvinoAsJson(ovino);
    }

    public JsonObject fetchOvinosForAnalise(String rfid1, String rfid2) {
        Ovino ovino1 = ovinoRepository.findByRfid(Long.parseLong(rfid1));
        Ovino ovino2 = ovinoRepository.findByRfid(Long.parseLong(rfid2));

        JsonObject response = new JsonObject();
        response.add("ovino1", getOvinoAsJson(ovino1));
        response.add("ovino2", getOvinoAsJson(ovino2));

        return response;
    }

    private JsonObject getOvinoAsJson(Ovino ovino) {
        JsonObject ovinoJson = new JsonObject();

        if (ovino == null) {
            ovinoJson.addProperty("error", "Nenhum animal encontrado com este RFID.");
            return ovinoJson;
        }

        ovinoJson.addProperty("rfid", ovino.getRfid());
        ovinoJson.addProperty("nome", ovino.getNome());
        ovinoJson.addProperty("raca", ovino.getRaca().name());
        ovinoJson.addProperty("grauPureza", ovino.getEnumGrauPureza().name());
        ovinoJson.addProperty("sexo", ovino.getSexo().name());
        ovinoJson.addProperty("status", ovino.getStatus().name());
        ovinoJson.addProperty("dataNascimento", ovino.getDataNascimento() != null ? ovino.getDataNascimento().toLocalDate().toString() : "Desconhecida");

        if (ovino.getDataNascimento() != null) {
            long meses = ChronoUnit.MONTHS.between(
                    ovino.getDataNascimento().toLocalDate(),
                    LocalDateTime.now().toLocalDate()
            );
            ovinoJson.addProperty("idadeMeses", meses);
        }

        if (ovino.getPesagens() != null && !ovino.getPesagens().isEmpty()) {
            var ultimaPesagem = ovino.getPesagens().get(ovino.getPesagens().size() - 1);
            ovinoJson.addProperty("pesoAtualKg", ultimaPesagem.getPeso());
        }

        // Ancestry (one level deep to avoid infinite recursion)
        if (ovino.getOvinoMae() != null) {
            JsonObject maeJson = new JsonObject();
            maeJson.addProperty("rfid", ovino.getOvinoMae().getRfid());
            maeJson.addProperty("nome", ovino.getOvinoMae().getNome());
            maeJson.addProperty("raca", ovino.getOvinoMae().getRaca().name());
            maeJson.addProperty("grauPureza", ovino.getOvinoMae().getEnumGrauPureza().name());
            ovinoJson.add("mae", maeJson);
        } else {
            ovinoJson.addProperty("mae", "Desconhecida");
        }

        if (ovino.getOvinoPai() != null) {
            JsonObject paiJson = new JsonObject();
            paiJson.addProperty("rfid", ovino.getOvinoPai().getRfid());
            paiJson.addProperty("nome", ovino.getOvinoPai().getNome());
            paiJson.addProperty("raca", ovino.getOvinoPai().getRaca().name());
            paiJson.addProperty("grauPureza", ovino.getOvinoPai().getEnumGrauPureza().name());
            ovinoJson.add("pai", paiJson);
        } else {
            ovinoJson.addProperty("pai", "Desconhecido");
        }

        List<OcorrenciaDoenca> ocorrenciasDoenca = ocorrenciaDoencaRepository.findByOvino(ovino);
        if (ocorrenciasDoenca != null && !ocorrenciasDoenca.isEmpty()) {
            JsonArray doencasArray = new JsonArray();
            for (OcorrenciaDoenca od : ocorrenciasDoenca) {
                JsonObject doencaJson = new JsonObject();
                doencaJson.addProperty("nome", od.getDoenca().getNome());
                doencaJson.addProperty("dataInicio", od.getDataInicio() != null ? od.getDataInicio().toLocalDate().toString() : "Desconhecida");
                doencaJson.addProperty("dataFinal", od.getDataFinal() != null ? od.getDataFinal().toLocalDate().toString() : "Em andamento");
                doencaJson.addProperty("curada", od.getCurada() != null ? od.getCurada() : false);
                doencasArray.add(doencaJson);
            }
            ovinoJson.add("historicoDoencas", doencasArray);
        }

        if (ovino.getCompra() != null) {
            JsonObject compraJson = new JsonObject();
            compraJson.addProperty("dataCompra", ovino.getCompra().getDataCompra() != null ? ovino.getCompra().getDataCompra().toLocalDate().toString() : "Desconhecida");
            compraJson.addProperty("valor", ovino.getCompra().getValor());
            if (ovino.getCompra().getVendedor() != null) {
                JsonObject vendedorJson = new JsonObject();
                vendedorJson.addProperty("nome", ovino.getCompra().getVendedor().getNome());
                vendedorJson.addProperty("cpfCnpj", ovino.getCompra().getVendedor().getCpfCnpj());
                vendedorJson.addProperty("email", ovino.getCompra().getVendedor().getEmail());
                vendedorJson.addProperty("telefone", ovino.getCompra().getVendedor().getTelefone());
                compraJson.add("vendedor", vendedorJson);
            }
            ovinoJson.add("compra", compraJson);
        }

        if (ovino.getParto() != null) {
            JsonObject partoJson = new JsonObject();
            partoJson.addProperty("dataParto", ovino.getParto().getDataParto() != null ? ovino.getParto().getDataParto().toLocalDate().toString() : "Desconhecida");
            if (ovino.getParto().getGestacao() != null) {
                JsonObject gestacaoJson = new JsonObject();
                gestacaoJson.addProperty("idGestacao", ovino.getParto().getGestacao().getId());
                gestacaoJson.addProperty("dataGestacao", ovino.getParto().getGestacao().getDataGestacao() != null ? ovino.getParto().getGestacao().getDataGestacao().toString() : "Desconhecida");
                if (ovino.getParto().getGestacao().getOvelhaMae() != null) {
                    gestacaoJson.addProperty("maeGestacaoRfid", ovino.getParto().getGestacao().getOvelhaMae().getRfid());
                }
                if (ovino.getParto().getGestacao().getOvelhaPai() != null) {
                    gestacaoJson.addProperty("paiGestacaoRfid", ovino.getParto().getGestacao().getOvelhaPai().getRfid());
                }
                partoJson.add("gestacao", gestacaoJson);
            }
            ovinoJson.add("parto", partoJson);
        }

        ovinoJson.addProperty("fotoUrl", ovino.getFotoOvino() != null ? ovino.getFotoOvino() : "");

        return ovinoJson;
    }
}
