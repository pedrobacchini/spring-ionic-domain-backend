package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Pedido;
import com.github.pedrobacchini.springionicdomain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoResource {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {
        Pedido pedido = pedidoService.find(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public Page<Pedido> findPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "24") Integer linesPerPage,
            @RequestParam(defaultValue = "instante") String orderBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        return pedidoService.findPage(page, linesPerPage, orderBy, direction);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
//        Pedido pedido = categoriaService.fromDTO(categoriaDTO);
        pedido = pedidoService.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
