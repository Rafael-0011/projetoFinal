package com.example.backend.service.curriculoService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.dto.CurriculoDto.CurriculoAlterarDto;
import com.example.backend.dto.CurriculoDto.CurriculoAlterarStatusDto;
import com.example.backend.dto.CurriculoDto.CurriculoCadastraDto;
import com.example.backend.dto.CurriculoDto.CurriculoListagemDto;
import com.example.backend.model.CurriculoModel;

public interface CurriculoService {
    CurriculoModel atualizaStatus(Long id, CurriculoAlterarStatusDto alterarDto);

    CurriculoModel atualizaCurriculo(Long id, CurriculoAlterarDto alterarDto);

    CurriculoModel obterCurriculo(Long id);

    Page<CurriculoListagemDto> paginacaoCurriculo(Pageable pageable);

    CurriculoModel cadastraCurriculo(CurriculoCadastraDto cadastraDto);

    CurriculoModel obterCurriculoPeloEmail(String email);

    List<CurriculoModel> obterListaCurriculo();

}
