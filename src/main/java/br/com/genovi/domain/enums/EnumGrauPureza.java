package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumGrauPureza implements IEnum<String, String> {
    PURO_ORIGEM("PURO_ORIGEM", "Puro por Origem"),
    PURO_POR_CRUZA("PURO_POR_CRUZA", "Puro por Cruza"),
    CRUZADO_CONTROLADO("CRUZADO_CONTROLADO", "Cruzado controlado"),
    CRUZADO_INDETERMINADO("CRUZADO_INDETERMINADO", "Cruzado indeterminado");

    private String key;
    private String value;

    private EnumGrauPureza(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
