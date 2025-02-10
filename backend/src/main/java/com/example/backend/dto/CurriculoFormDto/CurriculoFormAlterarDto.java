package com.example.backend.dto.CurriculoFormDto;

import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.enumerate.EscolaridadeEnum;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record CurriculoFormAlterarDto(
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
