package com.example.backend.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.backend.enumerate.PermissionEnum.ADMIN_READ;
import static com.example.backend.enumerate.PermissionEnum.ADMIN_UPDATE;
import static com.example.backend.enumerate.RoleEnum.ADMIN;
import static com.example.backend.enumerate.RoleEnum.USER;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    @Value("${jwt.public.key}")
    private RSAPublicKey keyPub;
    @Value("${jwt.private.key}")
    private RSAPrivateKey keyPriv;

    private final FilterSecurity securityFilter;

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html" };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                // Permite acesso público a endpoints de autenticação, cadastro e documentação
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/user/cadastro").permitAll()

                                // Endpoints relacionados a currículos
                                .requestMatchers(GET, "/form").hasAnyRole(USER.name(), ADMIN.name())
                                .requestMatchers(POST, "/form").hasAnyRole(USER.name(), ADMIN.name())
                                .requestMatchers(PUT, "/form/user/{id}").hasAnyRole(USER.name(), ADMIN.name())
                                .requestMatchers(GET, "/form/{email}").hasAnyRole(USER.name(), ADMIN.name())
                                .requestMatchers(PUT, "/form/admin/{id}")
                                .hasAnyAuthority(ADMIN_UPDATE.getPermissionEnum())
                                .requestMatchers(GET, "/grafico/escolaridade")
                                .hasAnyAuthority(ADMIN_READ.getPermissionEnum())
                                .requestMatchers(GET, "/grafico/status").hasAnyAuthority(ADMIN_READ.getPermissionEnum())

                                // Endpoints relacionados ao usuário

                                // Todos os outros endpoints exigem autenticação
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyPub).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(keyPub).privateKey(keyPriv).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
