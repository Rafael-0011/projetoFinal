package com.example.backend.service.token;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.backend.model.UserModel;

@Service
public class TokenJwtService {
    private final JwtEncoder encoder;

    public TokenJwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 3600L;

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var principal = authentication.getPrincipal();
        Long userId = null;
        if (principal instanceof UserModel) {
            userId = ((UserModel) principal).getId();
        } else if (principal instanceof UserDetails) {
            throw new IllegalStateException("UserDetails encontrado, mas não contém ID diretamente");
        }

        var cleims = JwtClaimsSet.builder()
                .issuer("spring-security-jwt")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("id", userId)
                .claim("authorities", authorities)
                .build();
        return encoder.encode(JwtEncoderParameters.from(cleims)).getTokenValue();
    }
}