package com.example.backend.dto.CurriculoFormDto;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoFormModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;


public record CurriculoFormListagemDto(Long id, String name, String cpf, LocalDate nascimento, String email,
                                       String telefone, EscolaridadeEnum escolaridadeEnum, String funcao,
                                       List<CompetenciaModel> competencia, StatusEnum statusEnum) {

    public CurriculoFormListagemDto(CurriculoFormModel dadosCadastrado) {
        this(
                dadosCadastrado.getId(),
                dadosCadastrado.getName(),
                dadosCadastrado.getCpf(),
                dadosCadastrado.getNascimento(),
                dadosCadastrado.getEmail(),
                dadosCadastrado.getTelefone(),
                dadosCadastrado.getEscolaridadeEnum(),
                dadosCadastrado.getFuncao(),
                dadosCadastrado.getCompetencia(),
                dadosCadastrado.getStatusEnum()
        );
    }
}
