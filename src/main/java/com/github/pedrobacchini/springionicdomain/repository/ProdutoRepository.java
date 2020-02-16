package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Category;
import com.github.pedrobacchini.springionicdomain.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj " +
            "FROM Produto obj " +
            "INNER JOIN obj.categories cat " +
            "WHERE obj.nome LIKE %:nome% and cat IN :categorias")
    Page<Produto> search(@Param("name") String nome,
                         @Param("categories") List<Category> categories,
                         Pageable pageRequest);

    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingAndCategoriesIn(String nome,
                                                              List<Category> categories,
                                                              Pageable pageRequest);
}
