package br.com.genovi.infrastructure.geminiapi.service;

import br.com.genovi.infrastructure.geminiapi.config.GeminiConfig;
import br.com.genovi.infrastructure.geminiapi.model.ChatMessage;
import br.com.genovi.infrastructure.geminiapi.model.ChatRequest;
import br.com.genovi.infrastructure.geminiapi.model.ChatResponse;
import br.com.genovi.infrastructure.geminiapi.utils.GenoviFunctions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GeminiService {

    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    private final HttpClient httpClient;
    private final Gson gson;
    private final GeminiConfig config;
    private final String systemPrompt;
    private final GenoviDatabaseService databaseService;

    @Autowired
    public GeminiService(GeminiConfig config, GenoviDatabaseService databaseService) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .build();
        this.databaseService = databaseService;
        this.gson = new Gson();
        this.systemPrompt = """
                Você é um especialista em ovinos e ovinocultura que trabalha dentro do Sistema Genovi.
                
                O Genovi é um sistema de monitoramento para ovinos que usa chips individuais em cada animal para registrar histórico de saúde, facilitar diagnósticos, acompanhar tratamentos, rastrear em tempo real, avaliar carcaças e registrar a ascendência para apoiar o melhoramento genético do rebanho. O Genovi é exclusivo para ovinos.
                Seu papel é responder perguntas sobre ovinos e assuntos relacionados, mesmo quando o usuário escrever de forma incorreta. Sempre tente entender a intenção da pergunta.
                Antes de responder perguntas que envolvam dados específicos de animais, como RFID, raça, peso, histórico, saúde ou reprodução, você deve usar primeiro as ferramentas do Genovi. Não peça ao usuário informações que o sistema pode fornecer. As funções disponíveis incluem:
                
                Assuntos permitidos:
                Raças de ovinos
                Manejo e cuidados
                Alimentação
                Reprodução
                Doenças e saúde
                Lã e tosquia
                Carne, leite e derivados
                Genética e melhoramento
                Comportamento e curiosidades sobre ovinos
                Se a pergunta não tiver relação com ovinos, você deverá responder:
                "Desculpe, eu só respondo perguntas sobre ovinos e o sistema Genovi! 🐑"
                Estilo das respostas:
                Curto, direto e prático
                Linguagem simples
                Amigável
                Usar emojis de 🐑 quando fizer sentido
                Sempre que a resposta depender de dados do sistema, chame a função apropriada antes de responder. Sempre tente interpretar corretamente a intenção do usuário, mesmo com erros de digitação.
                """;
    }

    public ChatResponse processChat(ChatRequest request) {
        if (request == null || request.getContents() == null || request.getContents().isEmpty()) {
            return new ChatResponse("Por favor, faça uma pergunta sobre ovinos ou o sistema Genovi! 🐑", true);
        }

        JsonArray contents = new JsonArray();
        for (ChatMessage message : request.getContents()) {
            JsonObject content = new JsonObject();
            content.addProperty("role", message.getRole());
            JsonArray parts = new JsonArray();
            JsonObject part = new JsonObject();
            part.addProperty("text", message.getText());
            parts.add(part);
            content.add("parts", parts);
            contents.add(content);
        }


        try {
            logger.info("Iniciando a requisição para o Gemini com histórico de {} mensagens", contents.size());

            JsonArray functionDeclarations = new JsonArray();
            functionDeclarations.add(GenoviFunctions.getAnimalDataSchema());
            functionDeclarations.add(GenoviFunctions.getAnaliseReprodutiva());
            functionDeclarations.add(GenoviFunctions.getPesoIdeal());

            while (true) {
                JsonObject payload = createPayload(contents, functionDeclarations);
                HttpResponse<String> response = sendRequest(payload);
                logger.info("Resposta recebida do Gemini: {}", response.body());

                if (response.statusCode() != 200) {
                    return handleResponse(response);
                }

                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                JsonObject functionCall = extractFunctionCall(jsonResponse);

                if (functionCall != null) {
                    logger.info("Chamada de função detectada: {}", functionCall.get("name").getAsString());

                    // Add the function call to the conversation history
                    JsonObject modelFunctionCallMessage = new JsonObject();
                    modelFunctionCallMessage.addProperty("role", "model");
                    JsonArray modelParts = new JsonArray();
                    JsonObject modelPart = new JsonObject();
                    modelPart.add("functionCall", functionCall);
                    modelParts.add(modelPart);
                    modelFunctionCallMessage.add("parts", modelParts);
                    contents.add(modelFunctionCallMessage);

                    // Execute the function
                    JsonObject functionResult = executeFunction(functionCall);

                    // Add the function response to the conversation history
                    JsonObject functionResponseMessage = new JsonObject();
                    functionResponseMessage.addProperty("role", "tool");
                    JsonArray toolParts = new JsonArray();
                    JsonObject toolPart = new JsonObject();
                    JsonObject functionResponseContent = new JsonObject();
                    functionResponseContent.addProperty("name", functionCall.get("name").getAsString());
                    functionResponseContent.add("response", functionResult);
                    toolPart.add("functionResponse", functionResponseContent);
                    toolParts.add(toolPart);
                    functionResponseMessage.add("parts", toolParts);
                    contents.add(functionResponseMessage);

                    // Continue the loop to send the updated history back to the model
                } else {
                    // No more function calls, handle the final text response
                    return handleResponse(response);
                }
            }

        } catch (IOException e) {
            logger.error("Erro de conectividade com a API Gemini", e);
            return new ChatResponse("Erro de conexão. Verifique sua internet e tente novamente. 🐑", true);
        } catch (InterruptedException e) {
            logger.error("Requisição foi interrompida", e);
            Thread.currentThread().interrupt();
            return new ChatResponse("Operação cancelada. Tente novamente. 🐑", true);
        } catch (Exception e) {
            logger.error("Erro inesperado durante a comunicação com Gemini", e);
            return new ChatResponse("Ops! Algo deu errado. Nossa equipe técnica foi notificada. 🐑", true);
        }
    }

    private HttpResponse<String> sendRequest(JsonObject payload) throws IOException, InterruptedException {
        logger.info("Enviando payload para o Gemini: {}", payload.toString());
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getApiUrl() + "?key=" + config.getApiKey()))
                .header("Content-Type", "application/json")
                .header("User-Agent", "GenoviApp/1.0")
                .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private JsonObject createPayload(JsonArray contents, JsonArray functionDeclarations) {
        JsonObject payload = new JsonObject();
        payload.add("contents", contents);

        JsonObject systemInstruction = new JsonObject();
        JsonArray systemParts = new JsonArray();
        JsonObject systemPart = new JsonObject();
        systemPart.addProperty("text", systemPrompt);
        systemParts.add(systemPart);
        systemInstruction.add("parts", systemParts);
        payload.add("systemInstruction", systemInstruction);


        JsonArray tools = new JsonArray();
        JsonObject tool = new JsonObject();
        tool.add("functionDeclarations", functionDeclarations);
        tools.add(tool);
        payload.add("tools", tools);

        JsonObject generationConfig = new JsonObject();
        generationConfig.addProperty("temperature", config.getTemperature());
        generationConfig.addProperty("maxOutputTokens", config.getMaxTokens());
        generationConfig.addProperty("topP", 0.8);
        generationConfig.addProperty("topK", 10);
        payload.add("generationConfig", generationConfig);

        JsonArray safetySettings = new JsonArray();
        payload.add("safetySettings", safetySettings);

        return payload;
    }

    private JsonObject extractFunctionCall(JsonObject jsonResponse) {
        if (jsonResponse.has("candidates")) {
            JsonArray candidates = jsonResponse.getAsJsonArray("candidates");
            if (candidates.size() > 0) {
                JsonObject candidate = candidates.get(0).getAsJsonObject();
                if (candidate.has("content")) {
                    JsonObject content = candidate.getAsJsonObject("content");
                    if (content.has("parts")) {
                        JsonArray parts = content.getAsJsonArray("parts");
                        if (parts.size() > 0) {
                            JsonObject firstPart = parts.get(0).getAsJsonObject();
                            if (firstPart.has("functionCall")) {
                                return firstPart.getAsJsonObject("functionCall");
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private JsonObject executeFunction(JsonObject functionCall) {
        String functionName = functionCall.get("name").getAsString();
        JsonObject args = functionCall.getAsJsonObject("args");

        switch (functionName) {
            case "getOvinoByRfid": {
                String rfid = args.get("rfid").getAsString();
                return databaseService.fetchAnimalData(rfid);
            }
            case "getAnaliseReprodutiva": {
                String rfid1 = args.get("rfid1").getAsString();
                String rfid2 = args.get("rfid2").getAsString();
                return databaseService.fetchOvinosForAnalise(rfid1, rfid2);
            }
            case "getPesoIdeal": {
                String raca = args.get("raca").getAsString();
                String sexo = args.get("sexo").getAsString();
                String dataNascimentoStr = args.get("dataNascimento").getAsString();
                long idadeEmMeses;

                try {
                    LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
                    idadeEmMeses = ChronoUnit.MONTHS.between(dataNascimento, LocalDate.now());
                } catch (DateTimeParseException e) {
                    JsonObject error = new JsonObject();
                    error.addProperty("error", "Formato de data inválido. Por favor, use AAAA-MM-DD.");
                    return error;
                }

                Double pesoAtual = null;
                if (args.has("pesoAtual")) {
                    JsonElement pesoAtualElement = args.get("pesoAtual");
                    if (!pesoAtualElement.isJsonNull()) {
                        pesoAtual = pesoAtualElement.getAsDouble();
                    }
                }

                JsonObject dadosOvino = new JsonObject();
                dadosOvino.addProperty("raca", raca);
                dadosOvino.addProperty("sexo", sexo);
                dadosOvino.addProperty("idadeEmMesesCalculada", idadeEmMeses);
                dadosOvino.addProperty("dataNascimentoFornecida", dataNascimentoStr);


                if (pesoAtual != null) {
                    dadosOvino.addProperty("pesoAtualEmKg", pesoAtual);
                } else {
                    dadosOvino.addProperty("pesoAtualEmKg", "Não informado");
                }

                return dadosOvino;
            }
            default: {
                JsonObject error = new JsonObject();
                error.addProperty("error", "Função desconhecida ou inválida.");
                return error;
            }
        }
    }

    public String askGemini(String question) {
        ChatMessage message = new ChatMessage("user", question);
        ChatRequest request = new ChatRequest(List.of(message));
        ChatResponse response = processChat(request);
        return response.isSuccess() ? response.getResponse() : response.getError();
    }

    private ChatResponse handleResponse(HttpResponse<String> response) {
        logger.info("Resposta da API Gemini - Status: {}", response.statusCode());
        if (response.statusCode() == 200) {
            String answer = parseResponse(response.body());
            return new ChatResponse(answer);
        } else if (response.statusCode() == 429) {
            logger.warn("Rate limit atingido - Status: 429, Body: {}", response.body());
            return new ChatResponse("Muitas perguntas! Aguarde um momento e tente novamente. 🐑", true);
        } else if (response.statusCode() >= 500) {
            logger.error("Erro do servidor Gemini - Status: {}, Body: {}", response.statusCode(), response.body());
            return new ChatResponse("Serviço temporariamente indisponível. Tente novamente em alguns minutos. 🐑", true);
        } else {
            logger.error("Erro na API Gemini - Status: {}, Body: {}",
                    response.statusCode(), response.body());
            return new ChatResponse("Erro na comunicação com o serviço. Tente novamente. 🐑", true);
        }
    }

    private String parseResponse(String responseBody) {
        logger.info("Parsing response body: {}", responseBody);
        if (responseBody == null || responseBody.trim().isEmpty()) {
            logger.warn("Resposta vazia da API");
            return "Resposta vazia do serviço. Tente reformular sua pergunta. 🐑";
        }

        try {
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

            if (jsonResponse.has("error")) {
                JsonObject error = jsonResponse.getAsJsonObject("error");
                String errorMessage = error.has("message") ?
                        error.get("message").getAsString() : "Erro desconhecido";
                logger.error("Erro retornado pela API: {}", errorMessage);
                return "Erro no processamento. Tente uma pergunta diferente. 🐑";
            }

            JsonArray candidates = jsonResponse.getAsJsonArray("candidates");
            if (candidates == null || candidates.size() == 0) {
                // Check for promptFeedback
                if (jsonResponse.has("promptFeedback")) {
                    JsonObject feedback = jsonResponse.getAsJsonObject("promptFeedback");
                    if (feedback.has("blockReason")) {
                        String reason = feedback.get("blockReason").getAsString();
                        logger.warn("Prompt bloqueado. Razão: {}", reason);
                        return "Sua pergunta foi bloqueada por nossas políticas de segurança. Por favor, reformule. 🐑";
                    }
                }
                logger.warn("Nenhum candidato de resposta encontrado");
                return "Não consegui gerar uma resposta. Tente reformular sua pergunta. 🐑";
            }

            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();

            if (firstCandidate.has("finishReason") &&
                    !"STOP".equals(firstCandidate.get("finishReason").getAsString()) &&
                    !"MAX_TOKENS".equals(firstCandidate.get("finishReason").getAsString())) {
                logger.warn("Resposta finalizada por motivo inesperado: {}", firstCandidate.get("finishReason").getAsString());
                if ("SAFETY".equals(firstCandidate.get("finishReason").getAsString())) {
                    return "Não posso responder a essa pergunta. Faça uma pergunta sobre ovelhas! 🐑";
                }
            }

            if (!firstCandidate.has("content") || firstCandidate.getAsJsonObject("content").isJsonNull()) {
                logger.warn("Candidato não possui conteúdo.");
                return "Resposta incompleta recebida. Tente novamente. 🐑";
            }

            JsonObject content = firstCandidate.getAsJsonObject("content");
            if (content == null || !content.has("parts") || content.getAsJsonArray("parts").isJsonNull()) {
                logger.warn("Conteúdo da resposta é nulo ou não possui partes");
                return "Resposta incompleta. Tente novamente. 🐑";
            }

            JsonArray parts = content.getAsJsonArray("parts");
            if (parts == null || parts.size() == 0) {
                logger.warn("Partes da resposta não encontradas");
                return "Resposta mal formada. Tente uma pergunta diferente. 🐑";
            }

            if (!parts.get(0).getAsJsonObject().has("text")) {
                logger.warn("Parte da resposta não contém texto");
                return "Resposta sem texto recebida. Tente novamente. 🐑";
            }

            String text = parts.get(0).getAsJsonObject().get("text").getAsString();
            logger.info("Resposta processada com sucesso - {} caracteres", text.length());

            return text.trim();

        } catch (Exception e) {
            logger.error("Erro ao fazer parse da resposta JSON", e);
            logger.error("Corpo da resposta com erro: {}", responseBody);
            return "Erro ao processar resposta. Tente novamente em alguns instantes. 🐑";
        }
    }
}
