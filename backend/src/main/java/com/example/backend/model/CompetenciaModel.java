package com.example.backend.model;

import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.enumerate.CompetenciaEnum;
import com.example.backend.enumerate.NivelEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competencia")
public class CompetenciaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private CompetenciaEnum competenciaEnum;
  @Enumerated(EnumType.STRING)
  private NivelEnum nivelEnum;

  public CompetenciaModel(CompetenciaCadastroDto cadastroDto) {
    this.competenciaEnum = cadastroDto.competenciaEnum();
    this.nivelEnum = cadastroDto.nivelEnum();
  }

}
