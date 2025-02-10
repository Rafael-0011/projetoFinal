package com.example.backend.model;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "formulario")
public class CurriculoFormModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(nullable = false)
  private String cpf;
  private LocalDate nascimento;
  @Column(nullable = false, unique = true)
  @Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$")
  private String email;
  @Column(nullable = false)
  private String telefone;
  @Enumerated(EnumType.STRING)
  private EscolaridadeEnum escolaridadeEnum;
  private String funcao;
  @ManyToMany
  @JoinTable(name = "competencias", joinColumns = @JoinColumn(name = "formulario_id"), inverseJoinColumns = @JoinColumn(name = "competencia_id"))
  private List<CompetenciaModel> competencia;
  @Enumerated(EnumType.STRING)
  private StatusEnum statusEnum = StatusEnum.EMESPERA;

}
