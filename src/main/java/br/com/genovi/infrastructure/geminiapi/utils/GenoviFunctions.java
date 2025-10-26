package br.com.genovi.infrastructure.geminiapi.utils;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class GenoviFunctions {

    public static JsonObject getAnimalDataSchema() {
        JsonObject functionDeclaration = new JsonObject();
        functionDeclaration.addProperty("name", "getOvinoByRfid");
        functionDeclaration.addProperty("description", "Busca informações detalhadas de um (ovino, ovelha, carneiro, cordeiro) no sistema Genovi usando o número do rfid.");

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

    public static JsonObject getAnaliseReprodutiva(){
        JsonObject functionDeclaration = new JsonObject();
        functionDeclaration.addProperty("name", "getAnaliseReprodutiva");
        functionDeclaration.addProperty("description", "Busca e entende sobre ");

        // criar description com contexto para pegar a grau pureza mais raca de dois ovinos para entregar o possivel resultado

        return functionDeclaration;
    }

    // outra function para dar possivel tratamento/diagnostivo com a doença que o ovino tal tem!
}
