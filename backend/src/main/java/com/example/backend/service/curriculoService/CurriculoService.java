package com.example.backend.service.curriculoService;

import java.util.List;

import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarStatusReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoCadastraReqDto;
import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.model.CurriculoModel;

public interface CurriculoService {
    CurriculoModel atualizaStatus(Long id, CurriculoAlterarStatusReqDto alterarDto);

    CurriculoModel atualizaCurriculo(Long id, CurriculoAlterarReqDto alterarDto);

    Page<CurriculoListagemResDto> paginacaoCurriculo(Pageable pageable);

    CurriculoModel cadastraCurriculo(CurriculoCadastraReqDto cadastraDto);

    CurriculoModel obterCurriculoPeloEmail(String email);

    List<CurriculoModel> obterListaCurriculo();

}
