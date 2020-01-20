package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly = true)
    List<Estado> findAllByOrderByNome();
}
