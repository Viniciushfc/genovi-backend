package br.com.genovi.infrastructure.geminiapi.controller;

import br.com.genovi.infrastructure.geminiapi.model.ChatMessage;
import br.com.genovi.infrastructure.geminiapi.model.ChatRequest;
import br.com.genovi.infrastructure.geminiapi.model.ChatResponse;
import br.com.genovi.infrastructure.geminiapi.service.GeminiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/ia-agent/genovi")
@CrossOrigin(origins = "*")
@Validated
public class GeminiController {

    private static final Logger logger = LoggerFactory.getLogger(GeminiController.class);

    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody @Valid ChatRequest request) {
        try {
            logger.info("Nova requisição de chat recebida: {}", request);

            ChatResponse response = geminiService.processChat(request);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            logger.error("Erro no endpoint /chat", e);
            ChatResponse errorResponse = new ChatResponse(
                    "Erro interno do servidor. Tente novamente em alguns instantes. 🐑", true
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // Endpoint alternativo para compatibilidade (recebe String direta)
    @PostMapping("/chat/simple")
    public ResponseEntity<ChatResponse> chatSimple(@RequestBody String message) {
        try {
            logger.info("Nova requisição simple chat recebida");

            ChatMessage chatMessage = new ChatMessage("user", message);
            ChatRequest request = new ChatRequest(List.of(chatMessage));
            ChatResponse response = geminiService.processChat(request);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            logger.error("Erro no endpoint /chat/simple", e);
            ChatResponse errorResponse = new ChatResponse(
                    "Erro interno do servidor. Tente novamente em alguns instantes. 🐑", true
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Genovi - Especialista em Ovelhas",
                "message", "API funcionando perfeitamente! 🐑",
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
                "name", "Genovi API",
                "description", "Chatbot especializado em ovelhas e ovinocultura",
                "version", "1.0.0",
                "specialties", new String[]{
                        "Raças de ovelhas",
                        "Cuidados e manejo",
                        "Alimentação",
                        "Reprodução",
                        "Doenças e saúde",
                        "Tosquia e lã",
                        "Produtos derivados"
                },
                "endpoints", Map.of(
                        "chat", "POST /api/gemini/chat - Fazer perguntas sobre ovelhas (ChatRequest)",
                        "chatSimple", "POST /api/gemini/chat/simple - Fazer perguntas sobre ovelhas (String)",
                        "health", "GET /api/gemini/health - Status da API",
                        "info", "GET /api/gemini/info - Informações da API",
                        "test", "GET /api/gemini/test - Teste rápido da funcionalidade"
                )
        ));
    }

    @GetMapping("/test")
    public ResponseEntity<ChatResponse> test() {
        try {
            ChatMessage testMessage = new ChatMessage("user", "O que são ovelhas?");
            ChatRequest testRequest = new ChatRequest(List.of(testMessage));
            ChatResponse response = geminiService.processChat(testRequest);

            logger.info("Teste da API executado - Success: {}", response.isSuccess());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Erro no teste da API", e);
            ChatResponse errorResponse = new ChatResponse(
                    "Erro no teste: " + e.getMessage(), true
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}