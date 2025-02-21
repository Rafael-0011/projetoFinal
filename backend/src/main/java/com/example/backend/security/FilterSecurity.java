package com.example.backend.security;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.service.userService.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterSecurity extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Lazy
    public FilterSecurity(JwtDecoder jwtDecoder, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtDecoder = jwtDecoder;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var jwtToken = recuperarToken(request);

        if (jwtToken != null) {
            var jwt = jwtDecoder.decode(jwtToken);
            var email = jwt.getSubject();
            UserDetails usuario = userDetailsServiceImpl.loadUserByUsername(email);

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
}
