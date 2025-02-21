package com.example.backend.dto.res.GraficosDtoRes;

import com.example.backend.dto.req.GraficosReqDto.ObterDadoGraficoReqDto;

import java.util.List;

public record DadosGraficosResDto(
        List<ObterDadoGraficoReqDto> dadoGrafico
){}