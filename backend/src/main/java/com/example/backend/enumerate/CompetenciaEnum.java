package com.example.backend.enumerate;

import com.example.backend.service.GlobalService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CompetenciaEnum {
    JAVA,
    SPRING_BOOT,
    PYTHON,
    JAVASCRIPT,
    ANGULAR,
    REACT,
    SQL,
    DATABASE_ADMINISTRATION,
    DEVOPS,
    TESTES_AUTOMATIZADOS,
    GIT,
    LINUX,
    C_SHARP,
    GO,
    NODE_JS,
    CLOUD_COMPUTING,
    AWS,
    AZURE,
    MACHINE_LEARNING,
    DATA_SCIENCE,
    UI_UX_DESIGN,
    ANDROID,
    IOS,
    MICROSOFT_AZURE,
    DOCKER,
    KUBERNETES,
    TDD,
    BDD,
    AGILE,
    SCRUM,
    UML,
    SOLID,
    CI_CD,
    SECURITY,
    BIG_DATA,
    BLOCKCHAIN
    
    ;

    @JsonCreator
    public static CompetenciaEnum fromString(String value) {
        String normalized = GlobalService.removeAccents(value).replaceAll("[\\s/.]", "_").toUpperCase();
        return CompetenciaEnum.valueOf(normalized);
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
