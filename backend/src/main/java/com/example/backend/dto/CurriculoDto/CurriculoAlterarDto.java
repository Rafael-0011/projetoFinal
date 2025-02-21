package com.example.backend.dto.CurriculoDto;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.enumerate.EscolaridadeEnum;

import jakarta.validation.constraints.NotBlank;

public record CurriculoAlterarDto(
        @NotBlank(message = "Para altera precisa do ID") Long id,
        String name,
        String cpf,
        LocalDate nascimento,
        String email,
        String telefone,
        EscolaridadeEnum escolaridadeEnum,
        String funcao,
        List<CompetenciaCadastroDto> competencia
) {
}
