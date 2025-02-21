package com.example.backend.service.token;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {

    private final TokenJwtService tokenJwtService;
    private final AuthenticationManager authenticationManager;

    public TokenAuthenticationService(AuthenticationManager authenticationManager, TokenJwtService tokenJwtService) {
        this.authenticationManager = authenticationManager;
        this.tokenJwtService = tokenJwtService;
    }

    public String authenticate(String login, String senha) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return tokenJwtService.generateToken(authentication);
    }
}
