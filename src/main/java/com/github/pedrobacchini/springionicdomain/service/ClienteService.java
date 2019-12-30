package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.domain.Endereco;
import com.github.pedrobacchini.springionicdomain.dto.ClienteDTO;
import com.github.pedrobacchini.springionicdomain.dto.ClienteNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import com.github.pedrobacchini.springionicdomain.repository.ClienteRepository;
import com.github.pedrobacchini.springionicdomain.repository.EnderecoRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.DataIntegrityException;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente find(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                                + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        //Para ter certeza que e um atualização e nao uma inserção
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente clientPersisted = find(cliente.getId());
        updateData(clientPersisted, cliente);
        return clienteRepository.save(clientPersisted);
    }

    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não e possivel excluir cliente porque há pedidos relacionados");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                null,
                null,
                null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(
                null,
                clienteNewDTO.getNome(),
                clienteNewDTO.getEmail(),
                clienteNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteNewDTO.getTipo()),
                bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()));

        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(
                null,
                clienteNewDTO.getLogradouro(),
                clienteNewDTO.getNumero(),
                clienteNewDTO.getComplemento(),
                clienteNewDTO.getBairro(),
                clienteNewDTO.getCep(),
                cliente,
                cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if (clienteNewDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }
        return cliente;
    }

    private void updateData(Cliente clientPersisted, Cliente cliente) {
        clientPersisted.setNome(cliente.getNome());
        clientPersisted.setEmail(cliente.getEmail());
    }
}
