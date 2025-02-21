package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.enumerate.CompetenciaEnum;
import com.example.backend.enumerate.NivelEnum;
import com.example.backend.model.CompetenciaModel;

@Repository
public interface CompetenciaRepository extends JpaRepository<CompetenciaModel, Long> {
    CompetenciaModel findByCompetenciaEnumAndNivelEnum(CompetenciaEnum competenciaEnum, NivelEnum nivelEnum);
    
}