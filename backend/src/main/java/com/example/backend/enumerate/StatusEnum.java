package com.example.backend.enumerate;

import com.example.backend.service.GlobalService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum {
    APROVADO,
    REPROVADO,
    AGUARDANDO;

    @JsonCreator
    public static StatusEnum fromString(String value) {
        String normalized = GlobalService.removeAccents(value).replaceAll("\\s", "").toUpperCase();
        return StatusEnum.valueOf(normalized);
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
