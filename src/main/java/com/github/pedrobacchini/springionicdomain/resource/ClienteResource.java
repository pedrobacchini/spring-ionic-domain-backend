package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) { this.clienteService = clienteService; }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCliente(@PathVariable Integer id) {
        Cliente cliente = clienteService.buscarPeloID(id);
        return ResponseEntity.ok(cliente);
    }
}
