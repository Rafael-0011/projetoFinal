package com.example.backend.service.competenciaService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.dto.req.competenciaReqDto.CompetenciaCadastroReqDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.repository.CompetenciaRepository;

@Service
public class CompetenciaService {

    private final ModelMapper modelMapper;
    private final CompetenciaRepository competenciaRepository;

    public CompetenciaService(CompetenciaRepository competenciaRepository, ModelMapper modelMapper) {
        this.competenciaRepository = competenciaRepository;
        this.modelMapper = modelMapper;
    }

    public CompetenciaModel findOrCreateCompetencia(CompetenciaCadastroReqDto competenciaCadastroDto) {
        CompetenciaModel dados = competenciaRepository.findByCompetenciaEnumAndNivelEnum(
                competenciaCadastroDto.competenciaEnum(),
                competenciaCadastroDto.nivelEnum());

        if (dados == null) {
            dados = modelMapper.map(competenciaCadastroDto, CompetenciaModel.class);
            competenciaRepository.saveAndFlush(dados);
        }

        return dados;
    }




}
