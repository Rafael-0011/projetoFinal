package com.example.backend.service.curriculoService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import com.example.backend.config.exceptionHandle.NotFoundException;
import com.example.backend.dto.CurriculoDto.CurriculoAlterarDto;
import com.example.backend.dto.CurriculoDto.CurriculoAlterarStatusDto;
import com.example.backend.dto.CurriculoDto.CurriculoCadastraDto;
import com.example.backend.dto.CurriculoDto.CurriculoListagemDto;
import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoModel;
import com.example.backend.repository.CurriculoRepository;
import com.example.backend.service.CompetenciaService;
import com.example.backend.service.GlobalService;

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
    public CurriculoModel atualizaStatus(Long id, CurriculoAlterarStatusDto alterarDto) {
        CurriculoModel dadoObtido = curriculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
        dadoObtido.setStatusEnum(alterarDto.statusEnum());
        return curriculoRepository.save(dadoObtido);
    }

    @Override
    public CurriculoModel atualizaCurriculo(Long id, CurriculoAlterarDto alterarDto) {
        CurriculoModel dadoObtido = curriculoRepository.findById(alterarDto.id())
                .orElseThrow(() -> new ExpressionException("Currículo não encontrado"));

        atualizaCurriculo(alterarDto, dadoObtido);

        return curriculoRepository.save(dadoObtido);

    }

    @Override
    public CurriculoModel obterCurriculo(Long id) {
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
    }

    @Override
    public Page<CurriculoListagemDto> paginacaoCurriculo(Pageable pageable) {
        return curriculoRepository.findAll(pageable)
                .map(CurriculoListagemDto::new);

    }

    @Override
    public CurriculoModel cadastraCurriculo(CurriculoCadastraDto cadastraDto) {
        var validaDadoExistente = curriculoRepository.findByEmail(cadastraDto.email());

        if (validaDadoExistente.isPresent()) {
            throw new IllegalArgumentException("Currículo já cadastrado para este e-mail.");
        }

        var competencias = obterCompetencias(cadastraDto);
        CurriculoModel dado = modelMapper.map(cadastraDto, CurriculoModel.class);
        dado.setCompetencia(competencias);
        return curriculoRepository.save(dado);
    }

    private void atualizaCurriculo(CurriculoAlterarDto alterarDto, CurriculoModel curriculoModel) {
        globalService.atualizaDados(alterarDto.name(), curriculoModel::setName);
        globalService.atualizaDados(alterarDto.cpf(), curriculoModel::setCpf);
        globalService.atualizaDados(alterarDto.nascimento(), curriculoModel::setNascimento);
        globalService.atualizaDados(alterarDto.email(), curriculoModel::setEmail);
        globalService.atualizaDados(alterarDto.telefone(), curriculoModel::setTelefone);
        globalService.atualizaDados(alterarDto.escolaridadeEnum(), curriculoModel::setEscolaridadeEnum);
        globalService.atualizaDados(alterarDto.funcao(), curriculoModel::setFuncao);
        atualizaCurriculo(alterarDto.competencia(), curriculoModel::setCompetencia);
    }

    private void atualizaCurriculo(List<CompetenciaCadastroDto> competencia,
            Consumer<List<CompetenciaModel>> setCompetencia) {
        if (competencia != null && !competencia.isEmpty()) {

            List<CompetenciaModel> competencias = new ArrayList<>();

            for (CompetenciaCadastroDto dto : competencia) {
                CompetenciaModel model = competenciaService.findOrCreateCompetencia(dto);
                competencias.add(model);
            }
            setCompetencia.accept(competencias);
        }
    }

    private List<CompetenciaModel> obterCompetencias(CurriculoCadastraDto cadastraDto) {
        List<CompetenciaModel> dado = new ArrayList<>();

        for (CompetenciaCadastroDto dto : cadastraDto.competencia()) {
            CompetenciaModel competencia = competenciaService.findOrCreateCompetencia(dto);
            dado.add(competencia);
        }

        return dado;
    }

    @Override
    public CurriculoModel obterCurriculoPeloEmail(String email) {
        return curriculoRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
    }

    @Override
    public List<CurriculoModel> obterListaCurriculo() {
        return curriculoRepository.findAll();
    }
}
