package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumReproducao implements IEnum<String, String> {
    INSEMINACAO_ARTIFICIAL("INSEMINACAO_ARTIFICIAL", "Inseminação Artificial"),
    MONTA_NATURAL("MONTA_NATURAL", "Monta Natural");

    private final String key;
    private final String value;

    EnumReproducao(String key, String value) {
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
