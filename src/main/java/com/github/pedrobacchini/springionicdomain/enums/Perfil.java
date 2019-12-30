package com.github.pedrobacchini.springionicdomain.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private Integer cod;
    private String descricao;

    Perfil(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() { return cod; }

    public String getDescricao() { return descricao; }

    public static Perfil toEnum(Integer cod) {
        if (cod == null)
            return null;
        for (Perfil value : Perfil.values()) {
            if (value.getCod().equals(cod)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Id Inválido " + cod);
    }
}
