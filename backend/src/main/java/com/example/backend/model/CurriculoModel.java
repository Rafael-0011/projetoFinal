package com.example.backend.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
public class CurriculoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @CPF
  private String cpf;
  private LocalDate nascimento;
  @Email
  private String email;
  private String telefone;
  @Enumerated(EnumType.STRING)
  private EscolaridadeEnum escolaridadeEnum;
  private String funcao;
  @ManyToMany
  @JoinTable(name = "competencias", joinColumns = @JoinColumn(name = "formulario_id"), inverseJoinColumns = @JoinColumn(name = "competencia_id"))
  private List<CompetenciaModel> competencia;
  @Enumerated(EnumType.STRING)
  private StatusEnum statusEnum = StatusEnum.AGUARDANDO;
  @OneToOne()
  @JoinColumn(name = "user_id", unique = true)
  private UserModel user;
 


}
