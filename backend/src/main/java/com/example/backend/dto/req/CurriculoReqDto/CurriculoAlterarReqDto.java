package com.example.backend.dto.req.CurriculoReqDto;

import com.example.backend.dto.req.competenciaReqDto.CompetenciaCadastroReqDto;
import com.example.backend.enumerate.EscolaridadeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record CurriculoAlterarReqDto(
        Long id,
        String name,
        @CPF(message = "CPF inválido")
        String cpf,
        LocalDate nascimento,
        @Email
        String email,
        String telefone,
        EscolaridadeEnum escolaridadeEnum,
        String funcao,
        List<CompetenciaCadastroReqDto> competencia
) {
}
