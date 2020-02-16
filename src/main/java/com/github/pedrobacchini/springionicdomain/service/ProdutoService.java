package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.domain.Category;
import com.github.pedrobacchini.springionicdomain.domain.Produto;
import com.github.pedrobacchini.springionicdomain.repository.CategoryRepository;
import com.github.pedrobacchini.springionicdomain.repository.ProdutoRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final LocaleMessageSource localeMessageSource;

    private final ProdutoRepository produtoRepository;
    private final CategoryRepository categoryRepository;

    public Produto find(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "id", id, Produto.class.getName())));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page,
                                Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
//        return produtoRepository.search(nome, categorias, pageRequest);
        return produtoRepository.findDistinctByNomeContainingAndCategoriesIn(nome, categories, pageRequest);
    }
}
