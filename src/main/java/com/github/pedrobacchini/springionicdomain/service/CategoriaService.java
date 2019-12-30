package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import com.github.pedrobacchini.springionicdomain.dto.CategoriaDTO;
import com.github.pedrobacchini.springionicdomain.repository.CategoriaRepository;
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
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                                + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        //Para ter certeza que e um atualização e nao uma inserção
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria categoriaPersisted = find(categoria.getId());
        updateData(categoriaPersisted, categoria);
        return categoriaRepository.save(categoria);
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não e possivel excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() { return categoriaRepository.findAll(); }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria categoriaPersisted, Categoria categoria) {
        categoriaPersisted.setNome(categoria.getNome());
    }
}
