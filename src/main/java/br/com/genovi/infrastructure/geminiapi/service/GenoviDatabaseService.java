package br.com.genovi.infrastructure.geminiapi.service;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GenoviDatabaseService {

    private final OvinoRepository ovinoRepository;

    public JsonObject fetchAnimalData(String rfidString) {
        Long rfid = Long.parseLong(rfidString);
        Ovino ovino = ovinoRepository.findByRfid(rfid);

        JsonObject response = new JsonObject();

        if (ovino == null) {
            response.addProperty("error", "Nenhum animal encontrado com este RFID.");
            return response;
        }

        response.addProperty("rfid", ovino.getRfid());
        response.addProperty("nome", ovino.getNome());
        response.addProperty("raca", ovino.getRaca().name());
        response.addProperty("grauPureza", ovino.getEnumGrauPureza().name());
        response.addProperty("sexo", ovino.getSexo().name());
        response.addProperty("status", ovino.getStatus().name());
        response.addProperty("dataNascimento", ovino.getDataNascimento() != null ? ovino.getDataNascimento().toLocalDate().toString() : "Desconhecida");

        if (ovino.getDataNascimento() != null) {
            long meses = java.time.temporal.ChronoUnit.MONTHS.between(
                    ovino.getDataNascimento().toLocalDate(),
                    java.time.LocalDate.now()
            );
            response.addProperty("idadeMeses", meses);
        }

        // Peso atual
        if (ovino.getPesagens() != null && !ovino.getPesagens().isEmpty()) {
            var ultimaPesagem = ovino.getPesagens().get(ovino.getPesagens().size() - 1);
            response.addProperty("pesoAtualKg", ultimaPesagem.toString());
        }

        response.addProperty("mae", ovino.getOvinoMae() != null ? ovino.getOvinoMae().getNome() : "Desconhecida");
        response.addProperty("pai", ovino.getOvinoPai() != null ? ovino.getOvinoPai().getNome() : "Desconhecido");

        String origem = ovino.getCompra() != null ? "Compra" : (ovino.getParto() != null ? "Nascimento" : "Manual");
        response.addProperty("origem", origem);

        response.addProperty("fotoUrl", ovino.getFotoOvino() != null ? ovino.getFotoOvino() : "");

        return response;
    }
}
