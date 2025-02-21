package com.example.backend.service.curriculoService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.config.exceptionHandle.NotFoundException;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoAlterarStatusReqDto;
import com.example.backend.dto.req.CurriculoReqDto.CurriculoCadastraReqDto;
import com.example.backend.dto.req.competenciaReqDto.CompetenciaCadastroReqDto;
import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoModel;
import com.example.backend.repository.CurriculoRepository;
import com.example.backend.service.GlobalService;
import com.example.backend.service.competenciaService.CompetenciaService;

import jakarta.transaction.Transactional;

@Service
public class CurriculoServiceImpl implements CurriculoService {
    private final ModelMapper modelMapper;
    private final GlobalService globalService;
    private final CompetenciaService competenciaService;
    private final CurriculoRepository curriculoRepository;

    public CurriculoServiceImpl(CompetenciaService competenciaService, CurriculoRepository curriculoRepository,
            GlobalService globalService, ModelMapper modelMapper) {
        this.competenciaService = competenciaService;
        this.curriculoRepository = curriculoRepository;
        this.globalService = globalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CurriculoModel obterCurriculoPeloEmail(String email) {
        return curriculoRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currículo não encontrado"));
    }

    @Override
    @Transactional
    public CurriculoModel atualizaStatus(Long id, CurriculoAlterarStatusReqDto alterarDto) {
        CurriculoModel dadoObtido = curriculoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
    
        dadoObtido.setStatusEnum(alterarDto.statusEnum());
        
        return curriculoRepository.save(dadoObtido); // Atualiza no banco
    }

    @Override
    public CurriculoModel atualizaCurriculo(Long id, CurriculoAlterarReqDto alterarDto) {
        var dado = obterCurriculo(alterarDto.id());
        atualizaCurriculo(alterarDto, dado);   
        return curriculoRepository.save(dado);
    }

    @Override
    public Page<CurriculoListagemResDto> paginacaoCurriculo(Pageable pageable) {
        return curriculoRepository.findAll(pageable)
                .map(CurriculoListagemResDto::new);
    }

    @Override
    public CurriculoModel cadastraCurriculo(CurriculoCadastraReqDto cadastraDto) {
        var validaDadoExistente = curriculoRepository.findByEmail(cadastraDto.email());

        if (validaDadoExistente.isPresent()) {
            throw new IllegalArgumentException("Currículo já cadastrado para este e-mail.");
        }
        var competencias = obterCompetencias(cadastraDto);
        CurriculoModel dado = modelMapper.map(cadastraDto, CurriculoModel.class);
        dado.setCompetencia(competencias);
        return curriculoRepository.save(dado);
    }


    @Override
    public List<CurriculoModel> obterListaCurriculo() {
        return curriculoRepository.findAll();
    }

    private void atualizaCurriculo(CurriculoAlterarReqDto alterarDto, CurriculoModel curriculoModel) {
        globalService.atualizaDados(alterarDto.name(), curriculoModel::setName);
        globalService.atualizaDados(alterarDto.cpf(), curriculoModel::setCpf);
        globalService.atualizaDados(alterarDto.nascimento(), curriculoModel::setNascimento);
        globalService.atualizaDados(alterarDto.email(), curriculoModel::setEmail);
        globalService.atualizaDados(alterarDto.telefone(), curriculoModel::setTelefone);
        globalService.atualizaDados(alterarDto.escolaridadeEnum(), curriculoModel::setEscolaridadeEnum);
        globalService.atualizaDados(alterarDto.funcao(), curriculoModel::setFuncao);
        atualizaCurriculo(alterarDto.competencia(), curriculoModel::setCompetencia);
    }

    private void atualizaCurriculo(List<CompetenciaCadastroReqDto> competencia,
                                   Consumer<List<CompetenciaModel>> setCompetencia) {
        if (competencia != null && !competencia.isEmpty()) {
            List<CompetenciaModel> competencias = new ArrayList<>();
            for (CompetenciaCadastroReqDto dto : competencia) {
                CompetenciaModel model = competenciaService.findOrCreateCompetencia(dto);
                competencias.add(model);
            }
            setCompetencia.accept(competencias);
        }
    }

    private List<CompetenciaModel> obterCompetencias(CurriculoCadastraReqDto cadastraDto) {
        List<CompetenciaModel> dado = new ArrayList<>();
        for (CompetenciaCadastroReqDto dto : cadastraDto.competencia()) {
            CompetenciaModel competencia = competenciaService.findOrCreateCompetencia(dto);
            dado.add(competencia);
        }
        return dado;
    }

    private CurriculoModel obterCurriculo(Long id) {
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currículo não encontrado"));
    }

}
