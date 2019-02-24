package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

//    Deixa a transação ficar mais rapida e diminui o tempo de locking do banco de dados
    @Transactional(readOnly = true)
    Optional<Cliente> findByEmail(String email);
}
