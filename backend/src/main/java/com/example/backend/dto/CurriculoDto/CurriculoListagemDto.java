package com.example.backend.dto.CurriculoDto;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoModel;

public record CurriculoListagemDto(Long id, String name, String cpf, LocalDate nascimento, String email,
        String telefone, EscolaridadeEnum escolaridadeEnum, String funcao,
        List<CompetenciaModel> competencia, StatusEnum statusEnum) {

    public CurriculoListagemDto(CurriculoModel dadosCadastrado) {
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
                dadosCadastrado.getStatusEnum());
    }

}
