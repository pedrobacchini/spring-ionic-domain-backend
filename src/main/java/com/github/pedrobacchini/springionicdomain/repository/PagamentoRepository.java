package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.Estado;
import com.github.pedrobacchini.springionicdomain.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
