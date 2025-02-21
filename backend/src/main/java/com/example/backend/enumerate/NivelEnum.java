package com.example.backend.enumerate;

import com.example.backend.service.GlobalService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NivelEnum {
    BASICO,
    INTERMEDIARIO,
    AVANCADO
    ;

    @JsonCreator
    public static NivelEnum fromString(String value) {
        String normalized = GlobalService.removeAccents(value).replaceAll("[\\s/.]", "").toUpperCase();
        return NivelEnum.valueOf(normalized);
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
