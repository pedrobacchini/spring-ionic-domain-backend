package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
