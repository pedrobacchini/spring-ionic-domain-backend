package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Categoria;
import com.github.pedrobacchini.springionicdomain.dto.CategoriaDTO;
import com.github.pedrobacchini.springionicdomain.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria categoria = categoriaService.find(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria = categoriaService.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
//        para garantir que voce esta atualizando a categoria correta
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria.setId(id);
        categoria = categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDTO> categoriasDTO = categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "24") Integer linesPerPage,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Page<Categoria> categoriaPage = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriaDTOPage = categoriaPage.map(CategoriaDTO::new);
        return ResponseEntity.ok(categoriaDTOPage);
    }
}