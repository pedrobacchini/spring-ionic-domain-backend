package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.dto.ClientDTO;
import com.github.pedrobacchini.springionicdomain.dto.ClientNewDTO;
import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import com.github.pedrobacchini.springionicdomain.service.ClientService;
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
@RequestMapping("/client")
public class ClientResource {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<Client> find(@PathVariable Integer id) {
        Client client = clientService.find(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/email")
    public ResponseEntity<Client> findByEmail(@RequestParam(value = "value") String email) {
        Client client = clientService.findByEmail(email);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/profile")
    public ResponseEntity<ClientDTO> profile() {
        Client authenticationClient = clientService.getAuthenticationClient();
        return ResponseEntity.ok(new ClientDTO(authenticationClient));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
        Client client = clientService.fromDTO(clientNewDTO);
        client = clientService.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Integer id) {
//        to ensure that you are updating the correct client
        Client client = clientService.fromDTO(clientDTO);
        client.setId(id);
        clientService.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientsDTO = clients.stream().map(ClientDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(clientsDTO);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<ClientDTO>> findPage(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "24") Integer linesPerPage,
                                                    @RequestParam(defaultValue = "nome") String orderBy,
                                                    @RequestParam(defaultValue = "ASC") String direction) {
        Page<Client> clientPage = clientService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientDTO> clientsDTOPage = clientPage.map(ClientDTO::new);
        return ResponseEntity.ok(clientsDTOPage);
    }

    @PostMapping("/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file,
                                                     @AuthenticationPrincipal ClientUserDetails clientUserDetails) {
        URI uri = clientService.uploadProfilePicture(file, clientUserDetails);
        return ResponseEntity.created(uri).build();
    }
}
