package com.example.backend.service.curriculoService;

import java.util.List;

import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarStatusReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoCadastraReqDto;
import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import com.example.backend.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.model.CurriculoModel;

public interface CurriculoService {
    CurriculoModel atualizaStatus(Long id, CurriculoAlterarStatusReqDto alterarDto);

    CurriculoModel atualizaDadoCurriculo(CurriculoAlterarReqDto alterarDto);

    void cadastraCurriculo(CurriculoCadastraReqDto cadastraDto);

    CurriculoModel obterCurriculoId(Long id);

    List<CurriculoModel> obterListaCurriculo();

}
