package com.example.backend.controller;

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
import com.example.backend.dto.CurriculoDto.CurriculoAlterarDto;
import com.example.backend.dto.CurriculoDto.CurriculoAlterarStatusDto;
import com.example.backend.dto.CurriculoDto.CurriculoCadastraDto;
import com.example.backend.dto.CurriculoDto.CurriculoListagemDto;
import com.example.backend.service.curriculoService.CurriculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/form")
@CrossOrigin("*")
public class CurriculoCotroller {

    private final CurriculoService curriculoService;

    public CurriculoCotroller(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastraForm(
            @RequestBody @Valid CurriculoCadastraDto cadastraDto) {
        try {
            var dado = curriculoService.cadastraCurriculo(cadastraDto);
            return ResponseEntity.ok(new CurriculoListagemDto(dado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastra o status" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllCurriculo() {
        try {
            var dado = curriculoService.obterListaCurriculo();
            return ResponseEntity.ok(dado);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na paginação: " + e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obterCurriculo(
            @PathVariable String email) {
        try {
            var dado = curriculoService.obterCurriculoPeloEmail(email);
            return ResponseEntity.ok(new CurriculoListagemDto(dado));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter o formulário");
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> alteraCurriculo(
            @PathVariable Long id,
            @RequestBody @Valid CurriculoAlterarDto alterarDto) {
        try {
            var curriculoAtualizado = curriculoService.atualizaCurriculo(id, alterarDto);
            return ResponseEntity.ok(new CurriculoListagemDto(curriculoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao Altera o status");
        }
    }

    @Operation(summary = "Altera status do currículo", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "ID do currículo", required = true),
            @Parameter(name = "alterarDto", in = ParameterIn.QUERY, schema = @Schema(implementation = CurriculoAlterarStatusDto.class)),
    })
    @PutMapping("admin/{id}")
    public ResponseEntity<?> alteraStatus(@PathVariable Long id,
            @RequestBody @Valid CurriculoAlterarStatusDto alterarDto) {
        try {
            var statusAtualizado = curriculoService.atualizaStatus(id, alterarDto);
            return ResponseEntity.ok(new CurriculoListagemDto(statusAtualizado));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o status");
        }
    }

    /*
     * 
     * @GetMapping
     * public ResponseEntity<?> paginacaoCurriculo(@PageableDefault(size = 10)
     * Pageable pageable) {
     * try {
     * 
     * var paginacao = curriculoService.paginacaoCurriculo(pageable);
     * return ResponseEntity.ok(paginacao);
     * } catch (NotFoundException e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
     * body("Erro na paginação: " + e.getMessage());
     * }
     * }
     */

    /*
     * @GetMapping("/{id}")
     * public ResponseEntity<?> obterCurriculo(@PathVariable Long id) {
     * try {
     * 
     * var dado = curriculoService.obterCurriculo(id);
     * 
     * return ResponseEntity.ok(new CurriculoListagemDto(dado));
     * } catch (NotFoundException e) {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " +
     * e.getMessage());
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
     * body("Erro ao obter o Formulário");
     * }
     * }
     */
}
