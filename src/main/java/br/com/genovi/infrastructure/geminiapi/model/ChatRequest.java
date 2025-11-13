package br.com.genovi.infrastructure.geminiapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class ChatRequest {

    @NotEmpty(message = "O conteúdo não pode estar vazio")
    @Valid
    private List<ChatMessage> contents;

    public ChatRequest() {
    }

    public ChatRequest(List<ChatMessage> contents) {
        this.contents = contents;
    }

    public List<ChatMessage> getContents() {
        return contents;
    }

    public void setContents(List<ChatMessage> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ChatRequest{contents=" + contents + "}";
    }
}
