package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.GraficosDto.DadosGraficosDto;
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
    public ResponseEntity<?> ObterEscolaridade() {
        try {
            var dadoEscolaridade = dadoGraficoService.dadosGraficoEscolaridade();
            return ResponseEntity.ok(new DadosGraficosDto(dadoEscolaridade));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> ObterDados() {
        try {
            var dadoStatus = dadoGraficoService.dadosGraficoStatus();
            return ResponseEntity.ok(new DadosGraficosDto(dadoStatus));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
