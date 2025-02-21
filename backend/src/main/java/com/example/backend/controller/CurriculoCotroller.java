package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarStatusReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoCadastraReqDto;
import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import com.example.backend.dto.res.MessageRes.MessageResponse;
import com.example.backend.model.CurriculoModel;
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
    public ResponseEntity<MessageResponse> cadastraForm(@RequestBody @Valid CurriculoCadastraReqDto cadastraDto) {
            curriculoService.cadastraCurriculo(cadastraDto);
            return ResponseEntity.ok(new MessageResponse("Curriculo Cadastro Com Sucesso"));
    }

    @GetMapping
    public ResponseEntity<List<CurriculoListagemResDto>> findAllCurriculo() {
        List<CurriculoListagemResDto> lista = curriculoService.obterListaCurriculo()
                .stream()
                .map(CurriculoListagemResDto::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{email}")
    public ResponseEntity<CurriculoListagemResDto> obterCurriculo(@PathVariable String email) {
        return ResponseEntity.ok(new CurriculoListagemResDto(curriculoService.obterCurriculoPeloEmail(email)));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<CurriculoListagemResDto> alteraCurriculo(@PathVariable Long id,
            @RequestBody @Valid CurriculoAlterarReqDto alterarDto) {
        return ResponseEntity.ok(new CurriculoListagemResDto(curriculoService.atualizaCurriculo(id, alterarDto)));
    }

    @Operation(summary = "Altera status do currículo", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "ID do currículo", required = true),
            @Parameter(name = "alterarDto", in = ParameterIn.QUERY, schema = @Schema(implementation = CurriculoAlterarStatusReqDto.class)),
    })

    @PutMapping("admin/{id}")
    public ResponseEntity<CurriculoListagemResDto> alteraStatus(@PathVariable Long id,
            @RequestBody @Valid CurriculoAlterarStatusReqDto alterarDto) {
        CurriculoModel atualizado = curriculoService.atualizaStatus(id, alterarDto);
        return ResponseEntity.ok(new CurriculoListagemResDto(atualizado));
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
