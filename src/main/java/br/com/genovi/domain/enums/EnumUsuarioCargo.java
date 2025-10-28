package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumUsuarioCargo implements IEnum<String, String> {
    ADMINISTRADOR("ADMINISTRADOR", "Administrador"),
    VETERINARIO("VETERINARIO", "Veterinário"),
    TRATADOR("TRATADOR", "Tratador");

    private final String key;
    private final String value;

    EnumUsuarioCargo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }
}
