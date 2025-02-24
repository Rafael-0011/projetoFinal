package com.example.backend.controller;

import java.util.List;

import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarStatusReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoCadastraReqDto;
import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import com.example.backend.dto.res.MessageRes.MessageResponse;
import com.example.backend.model.CurriculoModel;
import com.example.backend.service.curriculoService.CurriculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/form")
@CrossOrigin("*")
public class CurriculoCotroller {

    private final CurriculoService curriculoService;

    public CurriculoCotroller(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> cadastraForm(@RequestBody @Valid CurriculoCadastraReqDto cadastraDto) {
            curriculoService.cadastraCurriculo(cadastraDto);
            return ResponseEntity.ok(new MessageResponse("Curriculo Cadastro Com Sucesso"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CurriculoListagemResDto>> findAllCurriculo() {
        List<CurriculoListagemResDto> lista = curriculoService.obterListaCurriculo()
                .stream()
                .map(CurriculoListagemResDto::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurriculoListagemResDto> obterCurriculo(@PathVariable Long id) {
        CurriculoModel dado = curriculoService.obterCurriculoId(id);
        return ResponseEntity.ok(new CurriculoListagemResDto(dado));
    }

    @PutMapping("admin/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<CurriculoListagemResDto> alteraStatus(@PathVariable Long id,
            @RequestBody @Valid CurriculoAlterarStatusReqDto alterarDto) {
        CurriculoModel atualizado = curriculoService.atualizaStatus(id, alterarDto);
        return ResponseEntity.ok(new CurriculoListagemResDto(atualizado));
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurriculoListagemResDto> alteraCurriculo(
                                                                   @RequestBody @Valid CurriculoAlterarReqDto alterarDto) {
        return ResponseEntity.ok(new CurriculoListagemResDto(curriculoService.atualizaCurriculo(alterarDto)));
    }

/*
      @GetMapping("/{email}")
      public ResponseEntity<CurriculoListagemResDto> obterCurriculo(@PathVariable String email) {
          return ResponseEntity.ok(new CurriculoListagemResDto(curriculoService.obterCurriculoPeloEmail(email)));
      }

      @GetMapping
      public ResponseEntity<?> paginacaoCurriculo(@PageableDefault(size = 10)
      Pageable pageable) {
      try {

      var paginacao = curriculoService.paginacaoCurriculo(pageable);
      return ResponseEntity.ok(paginacao);
      } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
      body("Erro na paginação: " + e.getMessage());
      }
      }

      @GetMapping("/{id}")
      public ResponseEntity<?> obterCurriculo(@PathVariable Long id) {
      try {

      var dado = curriculoService.obterCurriculo(id);

      return ResponseEntity.ok(new CurriculoListagemDto(dado));
      } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " +
      e.getMessage());
      } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
      body("Erro ao obter o Formulário");
      }
      }
     */
}
