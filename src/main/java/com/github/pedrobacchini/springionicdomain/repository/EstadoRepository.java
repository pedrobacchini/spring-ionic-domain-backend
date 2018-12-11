package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
