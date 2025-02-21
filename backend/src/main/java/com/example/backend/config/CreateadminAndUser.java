package com.example.backend.config;


import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.backend.enumerate.RoleEnum.ADMIN;
import static com.example.backend.enumerate.RoleEnum.USER;



@Configuration
public class CreateadminAndUser{
    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new CommandLineRunner() {
            @Override
            public void run(String[] args) throws Exception {
                if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                    UserModel admin = new UserModel();
                    admin.setEmail("admin@gmail.com");
                    admin.setPassword(passwordEncoder.encode("admin123")); // Criptografando a senha
                    admin.setRole(ADMIN);
                    
                    userRepository.save(admin);
                    System.out.println("✅ Admin artificial criado com sucesso!");
                } else {
                    System.out.println("⚠️ Admin já existe. Nenhuma ação necessária.");
                }
            }
        };
    }

    @Bean
    CommandLineRunner createUser(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (userRepository.findByEmail("user@gmail.com").isEmpty()) {
                UserModel admin = new UserModel();
                admin.setEmail("user@gmail.com");
                admin.setPassword(passwordEncoder.encode("user123")); // Criptografando a senha
                admin.setRole(USER);

                userRepository.save(admin);
                System.out.println("✅ User artificial criado com sucesso!");
            } else {
                System.out.println("⚠️ User já existe. Nenhuma ação necessária.");
            }
        };
    }
}
