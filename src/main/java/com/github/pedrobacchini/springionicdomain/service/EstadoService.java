package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Estado;
import com.github.pedrobacchini.springionicdomain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public List<Estado> findAll() { return estadoRepository.findAllByOrderByNome(); }
}
