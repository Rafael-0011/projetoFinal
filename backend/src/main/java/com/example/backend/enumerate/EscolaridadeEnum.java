package com.example.backend.enumerate;

import com.example.backend.service.GlobalService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EscolaridadeEnum {
  ANALFABETO,
  FUNDAMENTAL_COMPLETO,
  MEDIO_INCOMPLETO,
  MEDIO_COMPLETO,
  SUPERIOR_INCOMPLETO,
  SUPERIO_COMPLETO,
  MESTRADO,
  DOUTORADO,
  IGNORADO;

  @JsonCreator
  public static EscolaridadeEnum fromString(String value) {
    String normalized = GlobalService.removeAccents(value).replaceAll("[\\s/.]", "_").toUpperCase();
    return EscolaridadeEnum.valueOf(normalized);
  }

  @JsonValue
  public String toJson() {
    return this.name();
  }


}
