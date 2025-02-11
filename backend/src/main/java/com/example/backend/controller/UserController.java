package com.example.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.config.exceptionHandle.NotFoundException;
import com.example.backend.dto.UserDto.UserCadastroDto;
import com.example.backend.model.UserModel;
import com.example.backend.repository.CurriculoFormRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CurriculoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CurriculoService curriculoService;
    private final CurriculoFormRepository curriculoFormRepository;

    public UserController(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, CurriculoService curriculoService, CurriculoFormRepository curriculoFormRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.curriculoService = curriculoService;
        this.curriculoFormRepository = curriculoFormRepository;
    }


    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastraUser(@RequestBody UserCadastroDto userCadastroDto) {
        try {
            
            
            UserModel loginDados = modelMapper.map(userCadastroDto, UserModel.class);
            String senhaCriptada = passwordEncoder.encode(loginDados.getPassword());
            loginDados.setPassword(senhaCriptada);
            UserModel dadosSalvos = userRepository.save(loginDados);
            return ResponseEntity.ok(dadosSalvos);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastra");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obterCurriculo(@PathVariable String email) {
        try {
            var getUser = curriculoFormRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("Currículo não encontrado"));
            return ResponseEntity.ok(getUser);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter o formulário");
        }
    }
}
