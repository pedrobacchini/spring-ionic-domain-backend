package com.github.pedrobacchini.springionicdomain.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private Integer cod;
    private String description;

    Role(Integer cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static Role toEnum(Integer cod) {
        if (cod == null)
            return null;
        for (Role value : Role.values()) {
            if (value.getCod().equals(cod)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Id Inválido " + cod);
    }
}
