package br.com.genovi.infrastructure.geminiapi.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class GenoviFunctions {

    public static JsonObject getAnimalDataSchema() {
        JsonObject functionDeclaration = new JsonObject();
        functionDeclaration.addProperty("name", "getOvinoByRfid");
        functionDeclaration.addProperty("description", "Busca informações detalhadas de um único ovino (ovelha, carneiro, cordeiro) no sistema Genovi. Use esta função quando o usuário pedir dados de apenas um animal pelo rfid.");

        JsonObject parameters = new JsonObject();
        parameters.addProperty("type", "object");

        JsonObject properties = new JsonObject();
        JsonObject rfid = new JsonObject();
        rfid.addProperty("type", "string");
        rfid.addProperty("description", "O número de identificação do rfid do animal(ovinos), que pode ser uma sequência de números ou uma combinação alfanumérica.");
        properties.add("rfid", rfid);

        parameters.add("properties", properties);
        parameters.addProperty("required", "rfid");

        functionDeclaration.add("parameters", parameters);

        return functionDeclaration;
    }

    public static JsonObject getAnaliseReprodutiva() {
        JsonObject functionDeclaration = new JsonObject();
        functionDeclaration.addProperty("name", "getAnaliseReprodutiva");
        functionDeclaration.addProperty("description", "Realiza uma análise de compatibilidade reprodutiva entre dois ovinos para um possível cruzamento. A função compara os dados genéticos e de ascendência de ambos os animais e retorna uma análise sobre a viabilidade e os resultados esperados.");

        JsonObject parameters = new JsonObject();
        parameters.addProperty("type", "object");

        JsonObject properties = new JsonObject();

        JsonObject rfid1 = new JsonObject();
        rfid1.addProperty("type", "string");
        rfid1.addProperty("description", "O número de identificação RFID do primeiro ovino (macho ou fêmea).");
        properties.add("rfid1", rfid1);

        JsonObject rfid2 = new JsonObject();
        rfid2.addProperty("type", "string");
        rfid2.addProperty("description", "O número de identificação RFID do segundo ovino (macho ou fêmea).");
        properties.add("rfid2", rfid2);

        parameters.add("properties", properties);

        JsonArray required = new JsonArray();
        required.add("rfid1");
        required.add("rfid2");
        parameters.add("required", required);

        functionDeclaration.add("parameters", parameters);

        return functionDeclaration;
    }

    public static JsonObject getPesoIdeal() {
        JsonObject functionDeclaration = new JsonObject();
        functionDeclaration.addProperty("name", "getPesoIdeal");
        functionDeclaration.addProperty("description", "Analisa o peso ideal de um ovino com base na raça, sexo e data de nascimento. A aplicação calculará a idade em meses. Retorna uma faixa de peso considerada saudável e uma avaliação do peso atual se fornecido.");

        JsonObject parameters = new JsonObject();
        parameters.addProperty("type", "object");

        JsonObject properties = new JsonObject();

        JsonObject raca = new JsonObject();
        raca.addProperty("type", "string");
        raca.addProperty("description", "A raça do ovino (ex: 'Dorper', 'Santa Inês').");
        properties.add("raca", raca);

        JsonObject sexo = new JsonObject();
        sexo.addProperty("type", "string");
        sexo.addProperty("description", "O sexo do ovino ('MACHO' ou 'FEMEA').");
        properties.add("sexo", sexo);

        JsonObject dataNascimento = new JsonObject();
        dataNascimento.addProperty("type", "string");
        dataNascimento.addProperty("description", "A data de nascimento do ovino no formato AAAA-MM-DD.");
        properties.add("dataNascimento", dataNascimento);

        JsonObject pesoAtual = new JsonObject();
        pesoAtual.addProperty("type", "number");
        pesoAtual.addProperty("description", "O peso atual do ovino em quilogramas (opcional).");
        properties.add("pesoAtual", pesoAtual);

        parameters.add("properties", properties);

        JsonArray required = new JsonArray();
        required.add("raca");
        required.add("sexo");
        required.add("dataNascimento");
        parameters.add("required", required);

        functionDeclaration.add("parameters", parameters);

        return functionDeclaration;
    }
}
