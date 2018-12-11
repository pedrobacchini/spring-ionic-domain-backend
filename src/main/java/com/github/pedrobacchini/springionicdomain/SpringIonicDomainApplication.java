package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.domain.Estado;
import com.github.pedrobacchini.springionicdomain.domain.Produto;
import com.github.pedrobacchini.springionicdomain.repository.CategoriaRepository;
import com.github.pedrobacchini.springionicdomain.repository.CidadeRepository;
import com.github.pedrobacchini.springionicdomain.repository.EstadoRepository;
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
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;

    public SpringIonicDomainApplication(CategoriaRepository categoriaRepository,
                                        ProdutoRepository produtoRepository,
                                        EstadoRepository estadoRepository,
                                        CidadeRepository cidadeRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
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

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

    }
}
