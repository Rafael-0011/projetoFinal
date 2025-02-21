package com.example.backend.dto.competenciaDto;

import com.example.backend.enumerate.CompetenciaEnum;
import com.example.backend.enumerate.NivelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CompetenciaCadastroDto(
        @Schema(example = "JAVA")@NotBlank(message = "Coloque a competencia") CompetenciaEnum competenciaEnum,
        @Schema(example = "BASICO")@NotBlank(message = "Coloque seu nivel") NivelEnum nivelEnum

) {

}
