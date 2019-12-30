package com.github.pedrobacchini.springionicdomain.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria implements Serializable {

    private static final long serialVersionUID = -4422320111986670655L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos = new ArrayList<>();

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getCategorias().add(this);
    }

    public void addAllProduto(List<Produto> produtos) {
        this.produtos.addAll(produtos);
        produtos.forEach(produto -> produto.getCategorias().add(this));
    }

    public List<Produto> getProdutos() { return Collections.unmodifiableList(this.produtos); }
}
