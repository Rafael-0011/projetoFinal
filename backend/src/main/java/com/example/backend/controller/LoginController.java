package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.loginDto.LoginDto;
import com.example.backend.dto.loginDto.TokenDto;
import com.example.backend.service.token.TokenAuthenticationService;

@RestController
@Controller
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    private final TokenAuthenticationService authenticationService;

    public LoginController(
            TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        try {
            String tokenJWT = authenticationService.authenticate(loginDto.email(), loginDto.password());
            return ResponseEntity.ok(new TokenDto(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer login" + e.getMessage());
        }
    }
}
