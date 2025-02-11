package com.example.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.config.exceptionHandle.NotFoundException;
import com.example.backend.dto.CurriculoFormDto.CurriculoFormAlterarDto;
import com.example.backend.dto.CurriculoFormDto.CurriculoFormAlterarStatus;
import com.example.backend.dto.CurriculoFormDto.CurriculoFormCadastraDto;
import com.example.backend.dto.CurriculoFormDto.CurriculoFormListagemDto;
import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoFormModel;
import com.example.backend.repository.CurriculoFormRepository;
import com.example.backend.service.CompetenciaService;
import com.example.backend.service.CurriculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/form")
@CrossOrigin("*")
public class UserCurriculoCotroller {

    private final ModelMapper modelMapper;
    private final CurriculoFormRepository curriculoFormRepository;
    private final CompetenciaService competenciaService;
    private final CurriculoService curriculoService;

    public UserCurriculoCotroller(CompetenciaService competenciaService,
            CurriculoFormRepository curriculoFormRepository,
            ModelMapper modelMapper, CurriculoService curriculoService) {
        this.competenciaService = competenciaService;
        this.curriculoFormRepository = curriculoFormRepository;
        this.modelMapper = modelMapper;
        this.curriculoService = curriculoService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastraForm(@RequestBody @Valid CurriculoFormCadastraDto cadastraDto) {
        try {

            var validaDadoExistente = curriculoFormRepository.findByEmail(cadastraDto.email());

            if (validaDadoExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("dados ja existe no banco");
            }
            List<CompetenciaModel> competencias = new ArrayList<>();

            for (CompetenciaCadastroDto dto : cadastraDto.competencia()) {
                CompetenciaModel competencia = competenciaService.findOrCreateCompetencia(dto);
                competencias.add(competencia);
            }

            CurriculoFormModel dados = modelMapper.map(cadastraDto, CurriculoFormModel.class);
            dados.setCompetencia(competencias);
            curriculoFormRepository.save(dados);
            return ResponseEntity.ok(new CurriculoFormListagemDto(dados));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Erro ao cadastra o status" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listPage(@PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<CurriculoFormListagemDto> list = curriculoFormRepository.findAll(pageable)
                    .map(CurriculoFormListagemDto::new);
            return ResponseEntity.ok(list);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na paginação: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterCurriculo(@PathVariable Long id) {
        try {
            var getUser = curriculoFormRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
            return ResponseEntity.ok(new CurriculoFormListagemDto(getUser));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter o formulário");
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> alteraCurriculo(@PathVariable Long id,
            @RequestBody @Valid CurriculoFormAlterarDto alterarDto) {
        try {

            CurriculoFormModel dadoObtido = curriculoFormRepository.findById(alterarDto.id())
                    .orElseThrow(() -> new ExpressionException("Currículo não encontrado"));

            curriculoService.atualizaDados(alterarDto, dadoObtido);

            var dadosCadastrado = curriculoFormRepository.save(dadoObtido);
            return ResponseEntity.ok(new CurriculoFormListagemDto(dadosCadastrado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao Altera o status");
        }
    }

    @Operation(summary = "Altera status do currículo", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "ID do currículo", required = true),
            @Parameter(name = "alterarDto", in = ParameterIn.QUERY, schema = @Schema(implementation = CurriculoFormAlterarStatus.class)),
    })
    @PutMapping("admin/{id}")
    public ResponseEntity<?> alteraStatus(@PathVariable Long id,
            @RequestBody @Valid CurriculoFormAlterarStatus alterarDto) {
        try {
            CurriculoFormModel dadoObtido = curriculoFormRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
            dadoObtido.setStatusEnum(alterarDto.statusEnum());
            var dadosCadastrado = curriculoFormRepository.save(dadoObtido);
            return ResponseEntity.ok(new CurriculoFormListagemDto(dadosCadastrado));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o status");
        }
    }
}
