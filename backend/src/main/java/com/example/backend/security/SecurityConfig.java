package com.example.backend.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.example.backend.enumerate.Permission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.backend.enumerate.Permission.*;
import static com.example.backend.enumerate.Role.ADMIN;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static com.example.backend.enumerate.Role.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey keyPub;
    @Value("${jwt.private.key}")
    private RSAPrivateKey keyPriv;

    private final SecurityFilter securityFilter;

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

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
                                .requestMatchers(GET, "/form").hasAnyRole(USER.name(), ADMIN.name()) // Listagem de currículos
                                .requestMatchers(POST, "/form").hasAnyRole(USER.name(), ADMIN.name()) // Cadastro de currículos
                                .requestMatchers(GET, "/form/{id}").hasAnyRole(USER.name(), ADMIN.name()) // Obter currículo por ID
                                .requestMatchers(PUT, "/form/user/{id}").hasAnyRole(USER.name(), ADMIN.name()) // Alterar currículo (usuário comum)
                                .requestMatchers(PUT, "/form/admin/{id}").hasAnyAuthority(ADMIN_UPDATE.getPermission()) // Alterar status do currículo (admin)

                                // Endpoints relacionados a gráficos (apenas para admin)
                                .requestMatchers(GET, "/grafico/escolaridade").hasAnyAuthority(ADMIN_READ.getPermission())
                                .requestMatchers(GET, "/grafico/status").hasAnyAuthority(ADMIN_READ.getPermission())

                                // Endpoints relacionados ao usuário
                                .requestMatchers(GET, "/user/{email}").hasAnyRole(USER.name(), ADMIN.name()) // Obter currículo por email

                                // Todos os outros endpoints exigem autenticação
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        ;
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
