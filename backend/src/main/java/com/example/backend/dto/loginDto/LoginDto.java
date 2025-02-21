package com.example.backend.dto.loginDto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Para fazer LOGIN precisa do email") String email,
        @NotBlank(message = "Para fazer LOGIN precisa do password")  String password) {

}
