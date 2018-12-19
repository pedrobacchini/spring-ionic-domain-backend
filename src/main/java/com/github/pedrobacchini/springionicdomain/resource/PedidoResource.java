package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Pedido;
import com.github.pedrobacchini.springionicdomain.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoResource {

    private final PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) { this.pedidoService = pedidoService; }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedido(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPeloID(id);
        return ResponseEntity.ok(pedido);
    }
}
