package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import com.github.pedrobacchini.springionicdomain.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringIonicDomainApplication implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    public SpringIonicDomainApplication(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDomainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escrítorio");
        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
    }
}
