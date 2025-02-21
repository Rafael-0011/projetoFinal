package com.example.backend.service.userService;

import com.example.backend.dto.req.UserReqDto.UserCadastroReqDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserModel cadastraUser(UserCadastroReqDto userCadastroDto) {
        UserModel loginDados = modelMapper.map(userCadastroDto, UserModel.class);
        String senhaCriptada = passwordEncoder.encode(loginDados.getPassword());
        loginDados.setPassword(senhaCriptada);

        return userRepository.save(loginDados);
    }
}
