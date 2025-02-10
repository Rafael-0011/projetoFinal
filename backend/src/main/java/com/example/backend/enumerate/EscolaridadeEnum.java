package com.example.backend.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EscolaridadeEnum {
  ANALFABETO,
  FUNDAMENTALCOMPLETO,
  MEDIOINCOMPLETO,
  MEDIOCOMPLETO,
  SUPERIORINCOMPLETO,
  SUPERIOCOMPLETO,
  MESTRADO,
  DOUTORADO,
  IGNORADO;


  @JsonCreator
  public static EscolaridadeEnum fromString(String value) {
    return EscolaridadeEnum.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return this.name();
  }
}
