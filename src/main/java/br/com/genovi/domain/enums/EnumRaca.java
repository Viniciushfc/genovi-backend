package br.com.genovi.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumRaca implements IEnum<String, String> {
    ILE_DE_FRANCE("ILE_DE_FRANCE", "Ile de France"),
    MERINO("MERINO", "Merino"),
    SUFFOLK("SUFFOLK", "Suffolk"),
    HAMPSHIRE("HAMPSHIRE", "Hampshire"),
    SOUTHDOWN("SOUTHDOWN", "Southdown"),
    DORSET_HORN("DORSET_HORN", "Dorset Horn"),
    CORRIEDALE("CORRIEDALE", "Corriedale"),
    ROMNEY("ROMNEY", "Romney"),
    LEICESTER("LEICESTER", "Leicester"),
    TEXEL("TEXEL", "Texel"),
    CHAROLLAIS("CHAROLLAIS", "Charollais"),
    CHEVIOT("CHEVIOT", "Cheviot"),
    JACOB("JACOB", "Jacob"),
    BRAZILIAN_SOMALI("BRAZILIAN_SOMALI", "Brazilian Somali"),
    DORPER("DORPER", "Dorper"),
    ASSAF("ASSAF", "Assaf"),
    AWASSI("AWASSI", "Awassi"),
    BLACKHEAD_PERSIAN("BLACKHEAD_PERSIAN", "Blackhead Persian"),
    VALAIS_BLACKNOSE("VALAIS_BLACKNOSE", "Valais Blacknose"),
    SHETLAND("SHETLAND", "Shetland"),
    NORTH_COUNTRY_CHEVIOT("NORTH_COUNTRY_CHEVIOT", "North Country Cheviot"),
    BORDER_LEICESTER("BORDER_LEICESTER", "Border Leicester"),
    CALIFORNIA_RED("CALIFORNIA_RED", "California Red"),
    COTSWOLD("COTSWOLD", "Cotswold"),
    KARAKUL("KARAKUL", "Karakul"),
    CASTLEMILK_MOORIT("CASTLEMILK_MOORIT", "Castlemilk Moorit"),
    HILL_RADNOR("HILL_RADNOR", "Hill Radnor"),
    FINNSHEEP("FINNSHEEP", "Finnsheep");

    private final String key;
    private final String value;

    EnumRaca(String key, String value) {
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
