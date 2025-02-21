package com.example.backend.service.userService;

import com.example.backend.dto.UserDto.UserCadastroDto;
import com.example.backend.model.UserModel;

public interface  UserService {
   UserModel cadastraUser(UserCadastroDto userCadastroDto);
}
