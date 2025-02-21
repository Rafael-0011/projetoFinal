package com.example.backend.service.dadoGraficoService;

import java.util.List;
import java.util.stream.Collectors;

import com.example.backend.dto.req.GraficosReqDto.ObterDadoGraficoReqDto;
import org.springframework.stereotype.Service;

import com.example.backend.repository.CurriculoRepository;

@Service
public class DadoGraficoServiceImpl implements DadoGraficoService {
    private final CurriculoRepository curriculoRepository;

    public DadoGraficoServiceImpl(CurriculoRepository curriculoRepository) {
        this.curriculoRepository = curriculoRepository;
    }

    @Override
    public List<ObterDadoGraficoReqDto> dadosGraficoEscolaridade() {
        return curriculoRepository.findAll().stream()
                .collect(Collectors.groupingBy(d -> d.getEscolaridadeEnum().toString(), Collectors.counting()))
                .entrySet().stream()
                .map(d -> new ObterDadoGraficoReqDto(d.getKey(), d.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ObterDadoGraficoReqDto> dadosGraficoStatus() {
        return curriculoRepository.findAll().stream()
                .collect(Collectors.groupingBy(d -> d.getStatusEnum().toString(), Collectors.counting()))
                .entrySet().stream()
                .map(d -> new ObterDadoGraficoReqDto(d.getKey(), d.getValue()))
                .collect(Collectors.toList());
    }
}