package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumSexo implements IEnum<String, String> {
    MACHO("MACHO", "Macho"),
    FEMEA("FEMEA", "Fêmea");

    private final String key;
    private final String value;

    EnumSexo(String key, String value) {
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
