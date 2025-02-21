package com.example.backend.dto.req.loginReqDto;

import jakarta.validation.constraints.NotBlank;

public record LoginReqDto(
        @NotBlank(message = "Para fazer LOGIN precisa do email") String email,
        @NotBlank(message = "Para fazer LOGIN precisa do password")  String password) {

}
