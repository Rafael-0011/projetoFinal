package com.example.backend.controller;

import com.example.backend.dto.DadosGraficosEscolaridade;
import com.example.backend.dto.DadosGraficosStatus;
import com.example.backend.dto.GetEscolaridadeDto;
import com.example.backend.dto.GetStatusDto;
import com.example.backend.repository.CurriculoFormRepository;
import com.example.backend.service.CompetenciaService;
import com.example.backend.service.CurriculoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grafico")
@CrossOrigin("*")
public class DadosParaGrafico {
    private final ModelMapper modelMapper;
    private final CurriculoFormRepository curriculoFormRepository;
    private final CompetenciaService competenciaService;
    private final CurriculoService curriculoService;

    public DadosParaGrafico(ModelMapper modelMapper, CurriculoFormRepository curriculoFormRepository, CompetenciaService competenciaService, CurriculoService curriculoService) {
        this.modelMapper = modelMapper;
        this.curriculoFormRepository = curriculoFormRepository;
        this.competenciaService = competenciaService;
        this.curriculoService = curriculoService;
    }


    @GetMapping("/escolaridade")
    public ResponseEntity<?> ObterEscolaridade() {
        try {
            var obterDados = curriculoFormRepository.findAll();
            List<GetEscolaridadeDto> contagem = obterDados.stream()
                    .collect(Collectors.groupingBy(d-> d.getEscolaridadeEnum().toString(),Collectors.counting()))
                    .entrySet().stream()
                    .map(d -> new GetEscolaridadeDto(d.getKey(), d.getValue()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new DadosGraficosEscolaridade(contagem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter dados do gráfico");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> ObterDados() {
        try {
            var obterDados = curriculoFormRepository.findAll();

            List<GetStatusDto> dadosGrafico = obterDados.stream()
                    .collect(Collectors.groupingBy(d->d.getStatusEnum().toString(),Collectors.counting()))
                    .entrySet().stream()
                    .map(d -> new GetStatusDto(d.getKey(), d.getValue()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new DadosGraficosStatus(dadosGrafico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter dados do gráfico");
        }
    }
}
