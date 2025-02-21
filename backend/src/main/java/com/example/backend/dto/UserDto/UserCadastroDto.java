package com.example.backend.dto.UserDto;


import jakarta.validation.constraints.NotBlank;

public record UserCadastroDto(
        @NotBlank(message = "Coloque seu email") String email,
        @NotBlank() String cpf,
        @NotBlank(message = "Coloque seu password") String password

)
{

}
