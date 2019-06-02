package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.pedrobacchini.springionicdomain.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
@JsonTypeName("PagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 2622178677977813411L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao() { }

    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() { return numeroDeParcelas; }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) { this.numeroDeParcelas = numeroDeParcelas; }
}
