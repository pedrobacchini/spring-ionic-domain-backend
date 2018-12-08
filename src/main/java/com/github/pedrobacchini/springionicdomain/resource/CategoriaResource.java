package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @GetMapping
    public List<Categoria> listar() {

        Categoria categoria1 = new Categoria(1, "Informatica");
        Categoria categoria2 = new Categoria(2, "Escritorio");

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria1);
        categorias.add(categoria2);

        return categorias;
    }
}
