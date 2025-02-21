package com.example.backend.controller;

import com.example.backend.dto.req.loginReqDto.LoginReqDto;
import com.example.backend.dto.res.loginResDto.TokenResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TokenResDto> authenticate(@RequestBody LoginReqDto loginDto) {
            String tokenJWT = authenticationService.authenticate(loginDto.email(), loginDto.password());
            return ResponseEntity.ok(new TokenResDto(tokenJWT));
    }
}
