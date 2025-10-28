package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatus implements IEnum<String, String> {
    ATIVO("ATIVO", "Ativo"),
    VENDIDO("VENDIDO", "Vendido"),
    DESATIVADO("DESATIVADO", "Desativado"),
    MORTO("MORTO", "Morto");

    private final String key;
    private final String value;

    EnumStatus(String key, String value) {
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
