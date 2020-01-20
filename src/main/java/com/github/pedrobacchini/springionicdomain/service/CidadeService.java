package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public List<Cidade> findAllByEstadoId(Integer estadoId) {
        return cidadeRepository.findAllByEstado_Id(estadoId);
    }
}
