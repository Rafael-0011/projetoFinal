package com.example.backend.controller;

import com.example.backend.dto.res.CurriculoDtoRes.CurriculoListagemResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.backend.dto.req.UserReqDto.UserCadastroReqDto;
import com.example.backend.dto.res.MessageRes.MessageResponse;
import com.example.backend.service.userService.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<MessageResponse> cadastraUser(@RequestBody UserCadastroReqDto userCadastroDto) {
        userService.cadastraUser(userCadastroDto);
        return ResponseEntity.ok(new MessageResponse("Usuario Criado Com Sucesso"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurriculoListagemResDto> obterCurriculoPeloIdUser(@PathVariable Long id) {
        return ResponseEntity.ok(new CurriculoListagemResDto(userService.obterCurriculoPeloIdUser(id).getCurriculo()));
    }
}
