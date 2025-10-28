package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumRole implements IEnum<String, String> {
    ROLE_USER("ROLE_USER", "ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN", "ROLE_ADMIN");

    private final String key;
    private final String value;

    EnumRole(String key, String value) {
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