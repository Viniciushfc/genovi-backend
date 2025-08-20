package br.com.genovi.geminiapi.service;

import br.com.genovi.geminiapi.config.GeminiConfig;
import br.com.genovi.geminiapi.model.ChatRequest;
import br.com.genovi.geminiapi.model.ChatResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

    @Autowired
    public GeminiService(GeminiConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .build();
        this.gson = new Gson();
        this.systemPrompt = """
                Você é um especialista em ovelhas e ovinocultura que trabalha dentro do sistema Genovi.
                O Genovi é um Sistema de Monitoramento para Ovinos que integra tecnologia para otimizar a gestão do rebanho. 
                Ele utiliza chips individuais em cada ovelha, armazenando seu histórico médico, facilitando diagnósticos e tratamentos. 
                Também oferece rastreamento em tempo real e tipificação de carcaça, permitindo avaliar a qualidade da produção. 
                Além disso, o sistema registra a ascendência do animal, auxiliando na seleção genética e no aprimoramento do rebanho. 
                O foco é o melhoramento genético, identificando características desejáveis para aumentar a eficiência e qualidade da criação. 
                Embora existam tecnologias semelhantes para bovinos, este sistema é voltado exclusivamente para ovinos.
                
                Seu papel é responder perguntas sobre ovelhas e temas relacionados, mesmo que o usuário use termos incorretos, traduções estranhas ou grafia incompleta.
                Sempre tente interpretar a intenção da pergunta antes de recusar.
                
                Assuntos aceitos:
                - Raças de ovelhas
                - Cuidados e manejo
                - Alimentação
                - Reprodução
                - Doenças e saúde
                - Tosquia e lã
                - Produtos derivados (carne, leite, lã)
                - Estudos genéticos sobre ovinos
                - Curiosidades sobre ovelhas
                
                Caso a pergunta não tenha nenhuma relação com ovinos, responda:
                "Desculpe, eu só respondo perguntas sobre ovelhas! 🐑"
                
                Seja informativo, amigável e use emojis de ovelha quando apropriado.
                Mantenha as respostas concisas, úteis e práticas.
                """;
    }

    public ChatResponse processChat(ChatRequest request) {
        if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            logger.warn("Requisição de chat vazia recebida");
            return new ChatResponse("Por favor, faça uma pergunta sobre ovelhas! 🐑", true);
        }

        try {
            logger.info("Processando chat - {} caracteres", request.getMessage().length());

            String fullPrompt = systemPrompt + "\n\nPergunta: " + request.getMessage().trim();
            JsonObject payload = createPayload(fullPrompt);

            HttpRequest httpRequest = buildRequest(payload);
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            return handleResponse(response);

        } catch (IOException e) {
            logger.error("Erro de conectividade com a API Gemini", e);
            return new ChatResponse("Erro de conexão. Verifique sua internet e tente novamente. 🐑", true);
        } catch (InterruptedException e) {
            logger.error("Requisição foi interrompida", e);
            Thread.currentThread().interrupt();
            return new ChatResponse("Operação cancelada. Tente novamente. 🐑", true);
        } catch (Exception e) {
            logger.error("Erro inesperado ao consultar Gemini", e);
            return new ChatResponse("Ops! Algo deu errado. Nossa equipe técnica foi notificada. 🐑", true);
        }
    }

    // Método para compatibilidade com versões anteriores
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
            logger.warn("Rate limit atingido - Status: 429");
            return new ChatResponse("Muitas perguntas! Aguarde um momento e tente novamente. 🐑", true);
        } else if (response.statusCode() >= 500) {
            logger.error("Erro do servidor Gemini - Status: {}", response.statusCode());
            return new ChatResponse("Serviço temporariamente indisponível. Tente novamente em alguns minutos. 🐑", true);
        } else {
            logger.error("Erro na API Gemini - Status: {}, Body: {}",
                    response.statusCode(), response.body());
            return new ChatResponse("Erro na comunicação com o serviço. Tente novamente. 🐑", true);
        }
    }

    private JsonObject createPayload(String prompt) {
        JsonObject payload = new JsonObject();

        // Estrutura do conteúdo
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();

        part.addProperty("text", prompt);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        payload.add("contents", contents);

        // Configurações de geração
        JsonObject generationConfig = new JsonObject();
        generationConfig.addProperty("temperature", config.getTemperature());
        generationConfig.addProperty("maxOutputTokens", config.getMaxTokens());
        generationConfig.addProperty("topP", 0.8);
        generationConfig.addProperty("topK", 10);
        payload.add("generationConfig", generationConfig);

        // Configurações de segurança (opcional)
        JsonArray safetySettings = new JsonArray();
        payload.add("safetySettings", safetySettings);

        return payload;
    }

    private String parseResponse(String responseBody) {
        if (responseBody == null || responseBody.trim().isEmpty()) {
            logger.warn("Resposta vazia da API");
            return "Resposta vazia do serviço. Tente reformular sua pergunta. 🐑";
        }

        try {
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

            // Verificar se há erros na resposta
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

            // Verificar se foi bloqueado por segurança
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