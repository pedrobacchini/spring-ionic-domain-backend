package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Produto;
import com.github.pedrobacchini.springionicdomain.dto.ProdutoDTO;
import com.github.pedrobacchini.springionicdomain.resource.utils.URL;
import com.github.pedrobacchini.springionicdomain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoResource {

    private final ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto produto = produtoService.find(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam String nome,
            @RequestParam String categorias,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "24") Integer linesPerPage,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        String nomeDecode = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> produtoPage = produtoService.search(nomeDecode, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtoDTOPage = produtoPage.map(ProdutoDTO::new);
        return ResponseEntity.ok(produtoDTOPage);
    }
}
