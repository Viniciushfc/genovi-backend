package br.com.genovi.infrastructure.geminiapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import br.com.genovi.infrastructure.geminiapi.config.GeminiConfig;
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
                Você é um especialista em ovinos e ovinocultura que trabalha dentro do sistema Genovi.
                O Genovi é um Sistema de Monitoramento para Ovinos que integra tecnologia para otimizar a gestão do rebanho.
                Ele utiliza chips individuais em cada ovelha, armazenando seu histórico médico, facilitando diagnósticos e tratamentos.
                Também oferece rastreamento em tempo real e tipificação de carcaça, permitindo avaliar a qualidade da produção.
                Além disso, o sistema registra a ascendência do animal, auxiliando na seleção genética e no aprimoramento do rebanho.
                O foco é o melhoramento genético, identificando características desejáveis para aumentar a eficiência e qualidade da criação.
                Embora existam tecnologias semelhantes para bovinos, este sistema é voltado exclusivamente para ovinos.
                
                                Seu papel é responder perguntas sobre ovinos e temas relacionados, mesmo que o usuário use termos incorretos, traduções estranhas ou grafia incompleta.
                                Sempre tente interpretar a intenção da pergunta antes de recusar.
                                **PRIORIZE O USO DAS FERRAMENTAS DISPONÍVEIS para obter informações do sistema Genovi antes de responder a perguntas que exijam dados específicos de ovinos (como RFID, raça, histórico de saúde, etc.). Não peça ao usuário por informações que podem ser obtidas através das ferramentas.**
                
                                Assuntos aceitos:
                                - Raças de ovinos
                                - Cuidados e manejo
                                - Alimentação
                                - Reprodução
                                - Doenças e saúde
                                - Tosquia e lã
                                - Produtos derivados (carne, leite, lã)
                                - Estudos genéticos sobre ovinos
                                - Curiosidades sobre ovinos
                Caso a pergunta não tenha nenhuma relação com ovinos, responda:
                "Desculpe, eu só respondo perguntas sobre ovinos e o sistema Genovi! 🐑"
                
                Seja informativo e objetivo não use frases longas, amigável e use emojis de ovelha quando apropriado.
                Mantenha as respostas concisas, úteis e práticas.
                """;
    }

    public ChatResponse processChat(ChatRequest request) {
        if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return new ChatResponse("Por favor, faça uma pergunta sobre ovinos ou o sistema Genovi! 🐑", true);
        }

        try {
            logger.info("Iniciando a requisição para o Gemini");

            JsonArray functionDeclarations = new JsonArray();
            functionDeclarations.add(GenoviFunctions.getAnimalDataSchema());
            functionDeclarations.add(GenoviFunctions.getAnaliseReprodutiva());
            functionDeclarations.add(GenoviFunctions.getPesoIdeal());


            String fullPrompt = systemPrompt + "\n\nPergunta: " + request.getMessage().trim();
            JsonObject payload = createPayload(fullPrompt, functionDeclarations);

            HttpResponse<String> response = sendRequest(payload);
            logger.info("Resposta recebida do Gemini: {}", response.body());
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

            JsonObject functionCall = extractFunctionCall(jsonResponse);
            if (functionCall != null) {
                logger.info("Chamada de função detectada: {}", functionCall.get("name").getAsString());

                JsonObject functionResult = executeFunction(functionCall);

                JsonObject secondPayload = createFunctionResponsePayload(
                        request.getMessage(),
                        functionCall,
                        functionResult
                );

                HttpResponse<String> finalResponse = sendRequest(secondPayload);
                return handleResponse(finalResponse);

            } else {
                return handleResponse(response);
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

    private JsonObject createPayload(String prompt, JsonArray functionDeclarations) {
        JsonObject payload = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);
        JsonArray parts = new JsonArray();
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        payload.add("contents", contents);

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

    private JsonObject createFunctionResponsePayload(String userPrompt, JsonObject functionCall, JsonObject functionResult) {
        JsonObject payload = new JsonObject();
        JsonArray contents = new JsonArray();

        String functionName = functionCall.get("name").getAsString();
        String fullUserPrompt = systemPrompt + "\n\nPergunta: " + userPrompt;

        if ("getOvinoByRfid".equals(functionName) && (userPrompt.toLowerCase().contains("peso") || userPrompt.toLowerCase().contains("gordo") || userPrompt.toLowerCase().contains("magro"))) {
            String analysisPrompt = "\n\nVocê é um especialista em ovinocultura. Com base nos seus conhecimentos sobre as diferentes raças de ovinos, e utilizando os dados retornados pela ferramenta a seguir, faça uma análise completa do peso do animal. Determine a faixa de peso ideal para um ovino com essa raça, sexo e idade. Em seguida, compare com o peso atual e dê um veredito claro: 'Peso Ideal', 'Abaixo do Peso' ou 'Acima do Peso'. Justifique sua análise de forma breve.";
            fullUserPrompt += analysisPrompt;
        } else if ("getAnaliseReprodutiva".equals(functionName)) {
            String analysisPrompt = "Analise a compatibilidade reprodutiva dos ovinos a seguir. Forneça análise detalhada (forças, fraquezas, riscos). Se houver dados ausentes, cite-os e explique o impacto na recomendação. Dê a melhor recomendação possível, mesmo com dados incompletos. Conclua explicitamente se a cruza é recomendada ou não.\n\n" +
                    "Dados dos Ovinos para Análise:\n" +
                    functionResult.toString();
            fullUserPrompt = systemPrompt + "\n\nPergunta: " + userPrompt + "\n\n" + analysisPrompt;
        } else if ("getPesoIdeal".equals(functionName)) {
            String analysisPrompt = "Com base nos dados a seguir, analise o peso do ovino e dê um veredito se ele está no peso ideal, acima ou abaixo do peso, fornecendo uma breve explicação. Dados do Ovino:\n" +
                    functionResult.toString();
            fullUserPrompt = systemPrompt + "\n\nPergunta: " + userPrompt + "\n\n" + analysisPrompt;
        }


        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        JsonArray userParts = new JsonArray();
        JsonObject userPart = new JsonObject();
        userPart.addProperty("text", fullUserPrompt);

        userParts.add(userPart);
        userMessage.add("parts", userParts);
        contents.add(userMessage);

        JsonObject modelFunctionCall = new JsonObject();
        modelFunctionCall.addProperty("role", "model");
        JsonArray modelParts = new JsonArray();
        JsonObject modelPart = new JsonObject();
        modelPart.add("functionCall", functionCall);
        modelParts.add(modelPart);
        modelFunctionCall.add("parts", modelParts);
        contents.add(modelFunctionCall);

        JsonObject functionResponse = new JsonObject();
        functionResponse.addProperty("role", "tool");
        JsonArray toolParts = new JsonArray();
        JsonObject toolPart = new JsonObject();
        JsonObject functionResponseContent = new JsonObject();
        functionResponseContent.addProperty("name", functionCall.get("name").getAsString());
        functionResponseContent.add("response", functionResult);
        toolPart.add("functionResponse", functionResponseContent);
        toolParts.add(toolPart);
        functionResponse.add("parts", toolParts);
        contents.add(functionResponse);

        payload.add("contents", contents);

        logger.info("Segundo payload enviado para o Gemini: {}", payload.toString());

        return payload;
    }

    private JsonObject extractFunctionCall(JsonObject jsonResponse) {
        if (jsonResponse.has("candidates")) {
            JsonObject candidate = jsonResponse.getAsJsonArray("candidates").get(0).getAsJsonObject();
            if (candidate.has("content")) {
                JsonObject content = candidate.getAsJsonObject("content");
                if (content.has("parts")) {
                    JsonObject firstPart = content.getAsJsonArray("parts").get(0).getAsJsonObject();
                    if (firstPart.has("functionCall")) {
                        return firstPart.getAsJsonObject("functionCall");
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
        ChatRequest request = new ChatRequest(question);
        ChatResponse response = processChat(request);
        return response.isSuccess() ? response.getResponse() : response.getError();
    }

    private HttpRequest buildRequest(JsonObject payload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(config.getApiUrl() + "?key=" + config.getApiKey()))
                .header("Content-Type", "application/json")
                .header("User-Agent", "GenoviApp/1.0")
                .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
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
                logger.warn("Nenhum candidato de resposta encontrado");
                return "Não consegui gerar uma resposta. Tente reformular sua pergunta. 🐑";
            }

            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();

            if (firstCandidate.has("finishReason") &&
                    "SAFETY".equals(firstCandidate.get("finishReason").getAsString())) {
                logger.warn("Resposta bloqueada por políticas de segurança");
                return "Não posso responder a essa pergunta. Faça uma pergunta sobre ovelhas! 🐑";
            }

            JsonObject content = firstCandidate.getAsJsonObject("content");
            if (content == null) {
                logger.warn("Conteúdo da resposta é nulo");
                return "Resposta incompleta. Tente novamente. 🐑";
            }

            JsonArray parts = content.getAsJsonArray("parts");
            if (parts == null || parts.size() == 0) {
                logger.warn("Partes da resposta não encontradas");
                return "Resposta mal formada. Tente uma pergunta diferente. 🐑";
            }

            String text = parts.get(0).getAsJsonObject().get("text").getAsString();
            logger.info("Resposta processada com sucesso - {} caracteres", text.length());

            return text.trim();

        } catch (Exception e) {
            logger.error("Erro ao fazer parse da resposta JSON", e);
            return "Erro ao processar resposta. Tente novamente em alguns instantes. 🐑";
        }
    }
}
