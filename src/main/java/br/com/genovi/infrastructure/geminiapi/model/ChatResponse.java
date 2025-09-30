package br.com.genovi.infrastructure.geminiapi.model;

import java.time.LocalDateTime;

public class ChatResponse {

    private String response;
    private boolean success;
    private String error;
    private LocalDateTime timestamp;

    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatResponse(String response) {
        this();
        this.response = response;
        this.success = true;
    }

    public ChatResponse(String error, boolean isError) {
        this();
        this.error = error;
        this.success = false;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}