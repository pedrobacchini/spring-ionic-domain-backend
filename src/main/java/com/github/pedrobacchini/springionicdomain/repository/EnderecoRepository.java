package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
