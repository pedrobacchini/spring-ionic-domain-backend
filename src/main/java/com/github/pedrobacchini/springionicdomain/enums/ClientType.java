package com.github.pedrobacchini.springionicdomain.enums;

import lombok.Getter;

@Getter
public enum ClientType {

    INDIVIDUAL(1, "Individual"),
    LEGAL_ENTITY(2, "Legal Entity");

    private Integer cod;
    private String description;

    ClientType(Integer cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static ClientType toEnum(Integer cod) {
        if (cod == null)
            return null;
        for (ClientType value : ClientType.values()) {
            if (value.getCod().equals(cod)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Id Inválido " + cod);
    }
}
