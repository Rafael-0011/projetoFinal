package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.res.GraficosDtoRes.DadosGraficosResDto;
import com.example.backend.service.dadoGraficoService.DadoGraficoService;

@RestController
@RequestMapping("/grafico")
@CrossOrigin("*")
public class GraficoController {

    private final DadoGraficoService dadoGraficoService;

    public GraficoController(DadoGraficoService dadoGraficoService) {
        this.dadoGraficoService = dadoGraficoService;
    }

    @GetMapping("/escolaridade")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<DadosGraficosResDto> ObterEscolaridade() {
        return ResponseEntity.ok(new DadosGraficosResDto(dadoGraficoService.dadosGraficoEscolaridade()));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<DadosGraficosResDto> ObterDados() {
        return ResponseEntity.ok(new DadosGraficosResDto(dadoGraficoService.dadosGraficoStatus()));
    }
}
