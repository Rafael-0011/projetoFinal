package com.example.backend.service.userService;

import com.example.backend.dto.req.UserReqDto.UserCadastroReqDto;
import com.example.backend.model.UserModel;

public interface  UserService {
   UserModel cadastraUser(UserCadastroReqDto userCadastroDto);
}
