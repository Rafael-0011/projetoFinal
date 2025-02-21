package com.example.backend.dto.req.CurriculoReqDto;

import com.example.backend.dto.req.competenciaReqDto.CompetenciaCadastroReqDto;
import com.example.backend.enumerate.EscolaridadeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CurriculoCadastraReqDto(
        @Schema(example = "rafael") @NotBlank(message = "Coloque seu name") String name,
        @Schema(example = "123456789") @NotBlank(message = "Coloque seu cpf") String cpf,
        @Schema(example = "2002-02-10") @NotNull (message = "Coloque seu nascimento") LocalDate nascimento,
        @Schema(example = "rafael@gmail.com") @NotBlank(message = "Coloque seu email") String email,
        @Schema(example = "85912345678") @NotBlank(message = "Coloque seu telefone") String telefone,
        @Schema(example = "MEDIOCOMPLETO") @NotNull(message = "Coloque seu escolaridade") EscolaridadeEnum escolaridadeEnum,
        @Schema(example = "desenvolverdor") @NotBlank(message = "Coloque seu funcao") String funcao,
        @NotNull(message = "Coloque seu competencia") List<CompetenciaCadastroReqDto> competencia

) {

}
