package com.github.pedrobacchini.springionicdomain.enums;

public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Juridíca");

    private Integer cod;
    private String descricao;

    TipoCliente(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCliente toEnum(Integer cod) {
        if(cod==null)
            return null;
        for (TipoCliente value : TipoCliente.values()) {
            if(value.getCod().equals(cod)){
                return value;
            }
        }
        throw new IllegalArgumentException("Id Inválido "+cod);
    }
}
