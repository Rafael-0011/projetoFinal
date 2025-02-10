package com.example.backend.controller;

import com.example.backend.dto.login.DadosTokenJWT;
import com.example.backend.dto.login.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.backend.service.tokem.AuthenticationService;

@RestController
@Controller
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
       try{
           String tokenJWT = authenticationService.authenticate(loginDto.email(),loginDto.password());

           return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer login");
       }
    }
}
