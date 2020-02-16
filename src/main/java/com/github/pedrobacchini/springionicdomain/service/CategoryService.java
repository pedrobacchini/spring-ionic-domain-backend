package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.domain.Category;
import com.github.pedrobacchini.springionicdomain.dto.CategoryDTO;
import com.github.pedrobacchini.springionicdomain.repository.CategoryRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.DataIntegrityException;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final LocaleMessageSource localeMessageSource;

    private final CategoryRepository categoryRepository;

    public Category find(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "id", id, Category.class.getName())));
    }

    public Category insert(Category category) {
        //Para ter certeza que e um atualização e nao uma inserção
        category.setId(null);
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        Category categoryPersisted = find(category.getId());
        updateData(categoryPersisted, category);
        return categoryRepository.save(category);
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(localeMessageSource.getMessage("not-delete-category-with-product"));
        }
    }

    public List<Category> findAll() { return categoryRepository.findAll(); }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoryRepository.findAll(pageRequest);
    }

    public Category fromDTO(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getName());
    }

    private void updateData(Category categoryPersisted, Category category) {
        categoryPersisted.setName(category.getName());
    }
}
