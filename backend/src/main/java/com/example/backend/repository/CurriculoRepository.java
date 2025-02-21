package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.CurriculoModel;

@Repository
public interface CurriculoRepository extends JpaRepository<CurriculoModel, Long> {
    Optional<CurriculoModel> findByEmail(String email);
    Optional<CurriculoModel> findByCpf(String cpf);

}