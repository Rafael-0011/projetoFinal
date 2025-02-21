package com.example.backend.dto.req.CurriculoReqDto;

import com.example.backend.dto.req.competenciaReqDto.CompetenciaCadastroReqDto;
import com.example.backend.enumerate.EscolaridadeEnum;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record CurriculoAlterarReqDto(
        @NotBlank(message = "Para altera precisa do ID") Long id,
        String name,
        String cpf,
        LocalDate nascimento,
        String email,
        String telefone,
        EscolaridadeEnum escolaridadeEnum,
        String funcao,
        List<CompetenciaCadastroReqDto> competencia
) {
}
