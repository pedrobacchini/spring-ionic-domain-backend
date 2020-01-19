package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.dto.ClienteDTO;
import com.github.pedrobacchini.springionicdomain.dto.ClienteNewDTO;
import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import com.github.pedrobacchini.springionicdomain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteResource {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/email")
    public ResponseEntity<Cliente> findByEmail(@RequestParam(value = "value") String email) {
        Cliente cliente = clienteService.findByEmail(email);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.fromDTO(clienteNewDTO);
        cliente = clienteService.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
//        para garantir que voce esta atualizando a cliente correta
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "24") Integer linesPerPage,
                                                     @RequestParam(defaultValue = "nome") String orderBy,
                                                     @RequestParam(defaultValue = "ASC") String direction) {
        Page<Cliente> clientePage = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clienteDTOPage = clientePage.map(ClienteDTO::new);
        return ResponseEntity.ok(clienteDTOPage);
    }

    @PostMapping("/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
                                                     @AuthenticationPrincipal ClientUserDetails clientUserDetails) {
        URI uri = clienteService.uploadProfilePicture(file, clientUserDetails);
        return ResponseEntity.created(uri).build();
    }
}
