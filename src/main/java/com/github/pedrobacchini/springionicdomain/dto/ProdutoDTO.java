package com.github.pedrobacchini.springionicdomain.dto;

import com.github.pedrobacchini.springionicdomain.domain.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private static final long serialVersionUID = -952714121053262964L;

    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoDTO() { }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
