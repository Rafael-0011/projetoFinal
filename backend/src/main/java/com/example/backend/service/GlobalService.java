package com.example.backend.service;

import java.text.Normalizer;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    public <T> void atualizaDados(T valor, Consumer<T> setter) {
        if (valor == null) return;
        if (valor instanceof String string && string.isEmpty()) return;
        if (valor instanceof List<?> list && list.isEmpty()) return;
        setter.accept(valor);
    }

    public static String removeAccents(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

}