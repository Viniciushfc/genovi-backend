package br.com.genovi.infrastructure.geminiapi.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {

    @NotBlank(message = "A mensagem não pode estar vazia")
    @Size(max = 1000, message = "A mensagem não pode ter mais que 1000 caracteres")
    private String message;

    public ChatRequest() {
    }

    public ChatRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatRequest{message='" + message + "'}";
    }
}