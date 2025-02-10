package com.example.backend.dto.CurriculoFormDto;

import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.enumerate.EscolaridadeEnum;
import com.example.backend.enumerate.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record CurriculoFormAlterarStatus(
            @Schema(description = "ID do currículo", example = "1")
            Long id,
            @Schema(description = "Novo status do currículo", example = "APROVADO")
            StatusEnum statusEnum
        ) {
}

