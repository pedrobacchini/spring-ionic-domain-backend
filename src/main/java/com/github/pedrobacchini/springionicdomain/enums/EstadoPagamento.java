package com.github.pedrobacchini.springionicdomain.enums;

public enum  EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer cod;
    private String descricao;

    EstadoPagamento(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer cod) {
        if(cod==null)
            return null;
        for (EstadoPagamento value : EstadoPagamento.values()) {
            if(value.getCod().equals(cod)){
                return value;
            }
        }
        throw new IllegalArgumentException("Id Inválido "+cod);
    }
}
