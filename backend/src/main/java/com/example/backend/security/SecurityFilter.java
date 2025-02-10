package com.example.backend.security;

import java.io.IOException;

import com.example.backend.config.exceptionHandle.NotFoundException;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.service.tokem.LoginDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final LoginDetailsImpl loginDetailsImpl;

    @Lazy
    public SecurityFilter(JwtDecoder jwtDecoder, LoginDetailsImpl loginDetailsImpl) {
        this.jwtDecoder = jwtDecoder;
        this.loginDetailsImpl = loginDetailsImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var jwtToken = recuperarToken(request);

        if (jwtToken != null) {
            var jwt = jwtDecoder.decode(jwtToken);
            var email = jwt.getSubject();
            UserDetails usuario = loginDetailsImpl.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    usuario,
                    null,
                    usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            logger.error("Token inválido ou expirado: " + e.getMessage());
            return false;
        }
    }

}
