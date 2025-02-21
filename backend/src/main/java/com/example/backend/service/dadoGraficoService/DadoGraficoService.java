package com.example.backend.service.dadoGraficoService;

import java.util.List;

import com.example.backend.dto.req.GraficosReqDto.ObterDadoGraficoReqDto;

public interface DadoGraficoService {
    List<ObterDadoGraficoReqDto> dadosGraficoEscolaridade();

    List<ObterDadoGraficoReqDto> dadosGraficoStatus();
}
