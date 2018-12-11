package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import com.github.pedrobacchini.springionicdomain.domain.Produto;
import com.github.pedrobacchini.springionicdomain.repository.CategoriaRepository;
import com.github.pedrobacchini.springionicdomain.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class SpringIonicDomainApplication implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public SpringIonicDomainApplication(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDomainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escrítorio");

        Produto produto1 = new Produto(null, "Computador", 2000D);
        Produto produto2 = new Produto(null, "Impressora", 800D);
        Produto produto3 = new Produto(null, "Mouse", 80D);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().add(produto2);

        produto1.getCategorias().add(categoria1);
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().add(categoria1);

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
    }
}
