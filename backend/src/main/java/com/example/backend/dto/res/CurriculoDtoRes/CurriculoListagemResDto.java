package com.example.backend.dto.res.CurriculoDtoRes;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoModel;

public record CurriculoListagemResDto(Long id, String name, String cpf, LocalDate nascimento, String email,
        String telefone, EscolaridadeEnum escolaridadeEnum, String funcao,
        List<CompetenciaModel> competencia, StatusEnum statusEnum, Long user) {

    public CurriculoListagemResDto(CurriculoModel dadosCadastrado) {
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
                dadosCadastrado.getStatusEnum(),
                dadosCadastrado.getUser().getId()
        );

    }

}
