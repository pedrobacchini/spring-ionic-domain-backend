package com.github.pedrobacchini.springionicdomain.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.domain.Estado;
import com.github.pedrobacchini.springionicdomain.json.View;
import com.github.pedrobacchini.springionicdomain.service.CidadeService;
import com.github.pedrobacchini.springionicdomain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estado")
public class EstadoResource {

    private final EstadoService estadoService;
    private final CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Estado>> findAll() {
        List<Estado> estados = estadoService.findAll();
        return ResponseEntity.ok(estados);
    }

    @JsonView(View.FindAll.class)
    @GetMapping("/{estadoId}/cidade")
    public ResponseEntity<List<Cidade>> findAllCidadesByEstado(@PathVariable Integer estadoId) {
        List<Cidade> cidades = cidadeService.findAllByEstadoId(estadoId);
        return ResponseEntity.ok(cidades);
    }
}
