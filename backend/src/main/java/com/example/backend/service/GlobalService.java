package com.example.backend.service;

import java.text.Normalizer;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    public <T> void atualizaDados(T valor, Consumer<T> setter) {
        if (valor != null) {
            if (valor instanceof String string) {
                if (!string.isEmpty()) {
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

    public static String removeAccents(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

}