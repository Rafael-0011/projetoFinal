package com.example.backend.dto.req.UserReqDto;


import jakarta.validation.constraints.NotBlank;

public record UserCadastroReqDto (
        @NotBlank(message = "Coloque seu email") String email,
        @NotBlank() String cpf,
        @NotBlank(message = "Coloque seu password") String password

)
{

}
