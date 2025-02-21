package com.example.backend.dto.CurriculoDto;

import com.example.backend.enumerate.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurriculoAlterarStatusDto(
            @Schema(description = "ID do currículo", example = "1")
            Long id,
            @Schema(description = "Novo status do currículo", example = "APROVADO")
            StatusEnum statusEnum
        ) {
}

