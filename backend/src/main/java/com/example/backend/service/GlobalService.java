package com.example.backend.service;

import com.example.backend.dto.competenciaDto.CompetenciaCadastroDto;
import com.example.backend.model.CompetenciaModel;
import com.example.backend.model.CurriculoFormModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class GlobalService {

    public <T> void atualizaDados(T valor, Consumer<T> setter){
        if (valor != null) {
            if (valor instanceof String) {
                if (!((String) valor).isEmpty()) {
                    setter.accept(valor);
                }
            } else if (valor instanceof List) {
                if (!((List<?>) valor).isEmpty()) {
                    setter.accept(valor);
                }
            } else {
                setter.accept(valor);
            }
        }
    }

}