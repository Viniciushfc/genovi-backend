package br.com.genovi.geminiapi.service;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class GenoviDatabaseService {

    private OvinoRepository ovinoRepository;

    public GenoviDatabaseService(OvinoRepository ovinoRepository) {
        this.ovinoRepository = ovinoRepository;
    }

    public JsonObject    fetchAnimalData(String rfidString) {
        Long rfid =  Long.parseLong(rfidString);
        Ovino ovino = ovinoRepository.findByRfid(rfid);

        if (ovino != null) {
            JsonObject ovinoData = new JsonObject();
            ovinoData.addProperty("rfid", ovino.getRfid());
            ovinoData.addProperty("raca", ovino.getRaca());
            ovinoData.addProperty("sexo", ovino.getSexo().toString());
            ovinoData.addProperty("tempo_de_fazenda", ovino.getTempoFazenda());
            return ovinoData;
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("error", "Nenhum animal encontrado com este RFID.");
            return error;
        }
    }
}
