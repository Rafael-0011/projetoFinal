package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
