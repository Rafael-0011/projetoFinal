package com.example.backend.dto.req.CurriculoReqDto;

import com.example.backend.enumerate.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record CurriculoAlterarStatusReqDto(
            @Schema(description = "ID do currículo", example = "1")
            Long id,
            @Schema(description = "Novo status do currículo", example = "APROVADO")
            StatusEnum statusEnum
        ) {
}

