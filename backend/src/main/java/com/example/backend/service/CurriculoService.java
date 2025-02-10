package com.example.backend.service;

import com.example.backend.dto.CurriculoFormDto.CurriculoFormAlterarDto;
import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoFormModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class CurriculoService {

    private final GlobalService globalService;
    private final CompetenciaService competenciaService;

    public CurriculoService(GlobalService globalService, CompetenciaService competenciaService) {
        this.globalService = globalService;
        this.competenciaService = competenciaService;
    }


    public void atualizaDados(CurriculoFormAlterarDto formAlterarDto, CurriculoFormModel formModel){
        globalService.atualizaDados(formAlterarDto.name(), formModel::setName);
        globalService.atualizaDados(formAlterarDto.cpf(), formModel::setCpf);
        globalService.atualizaDados(formAlterarDto.nascimento(), formModel::setNascimento);
        globalService.atualizaDados(formAlterarDto.email(), formModel::setEmail);
        globalService.atualizaDados(formAlterarDto.telefone(), formModel::setTelefone);
        globalService.atualizaDados(formAlterarDto.escolaridadeEnum(), formModel::setEscolaridadeEnum);
        globalService.atualizaDados(formAlterarDto.funcao(), formModel::setFuncao);
        atualizaDados(formAlterarDto.competencia(), formModel::setCompetencia);
    }

    public void atualizaDados(List<CompetenciaCadastroDto> competencia, Consumer<List<CompetenciaModel>> setCompetencia) {
        if (competencia != null && !competencia.isEmpty()) {

            List<CompetenciaModel> competencias = new ArrayList<>();

            for (CompetenciaCadastroDto dto : competencia) {
                CompetenciaModel model = competenciaService.findOrCreateCompetencia(dto);
                competencias.add(model);
            }
            setCompetencia.accept(competencias);
        }
    }
}
