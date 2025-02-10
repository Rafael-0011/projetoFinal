package com.example.backend.repository;

import com.example.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.CurriculoFormModel;

import java.util.Optional;

@Repository
public interface CurriculoFormRepository extends JpaRepository<CurriculoFormModel, Long> {
    Optional<CurriculoFormModel> findByEmail(String email);
    Optional<CurriculoFormModel> findByCpf(String cpf);

}