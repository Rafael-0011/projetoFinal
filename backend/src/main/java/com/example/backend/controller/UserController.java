package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.UserDto.UserCadastroDto;
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
    public ResponseEntity<?> cadastraUser(@RequestBody UserCadastroDto userCadastroDto) {
        try {
            var dado = userService.cadastraUser(userCadastroDto);
            return ResponseEntity.ok(dado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastra usuario" + e.getMessage());
        }
    }

}
