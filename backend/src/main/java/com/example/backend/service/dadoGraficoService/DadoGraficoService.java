package com.example.backend.service.dadoGraficoService;

import java.util.List;

import com.example.backend.dto.GraficosDto.ObterDadoGraficoDto;

public interface DadoGraficoService {
    List<ObterDadoGraficoDto> dadosGraficoEscolaridade();

    List<ObterDadoGraficoDto> dadosGraficoStatus();
}
